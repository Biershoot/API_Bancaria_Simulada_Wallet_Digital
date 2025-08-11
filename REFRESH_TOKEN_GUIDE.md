# Guía de Implementación de Refresh Token en el Cliente

## 📋 Descripción

Esta guía explica cómo implementar el sistema de Refresh Token en el lado del cliente para mantener sesiones activas de manera segura.

## 🔄 Concepto de Refresh Token

### **¿Qué es?**
- **Access Token**: Token de corta duración (15 minutos) para autenticación
- **Refresh Token**: Token de larga duración (7 días) para renovar access tokens

### **Flujo de Seguridad:**
1. Usuario hace login → Recibe access token + refresh token
2. Access token expira → Cliente usa refresh token para obtener nuevo access token
3. Refresh token expira → Usuario debe hacer login nuevamente

## 💻 Implementación en el Cliente

### **JavaScript/TypeScript**

```javascript
class AuthService {
    constructor() {
        this.accessToken = localStorage.getItem('accessToken');
        this.refreshToken = localStorage.getItem('refreshToken');
    }

    // Login y guardar tokens
    async login(username, password) {
        try {
            const response = await fetch('/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();
                this.accessToken = data.accessToken;
                this.refreshToken = data.refreshToken;
                
                // Guardar en localStorage
                localStorage.setItem('accessToken', this.accessToken);
                localStorage.setItem('refreshToken', this.refreshToken);
                
                return data;
            }
        } catch (error) {
            console.error('Error en login:', error);
            throw error;
        }
    }

    // Refrescar access token
    async refreshAccessToken() {
        try {
            const response = await fetch('/auth/refresh', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ refreshToken: this.refreshToken })
            });

            if (response.ok) {
                const data = await response.json();
                this.accessToken = data.accessToken;
                localStorage.setItem('accessToken', this.accessToken);
                return this.accessToken;
            } else {
                // Refresh token expirado, redirigir al login
                this.logout();
                throw new Error('Refresh token expirado');
            }
        } catch (error) {
            console.error('Error refrescando token:', error);
            this.logout();
            throw error;
        }
    }

    // Hacer petición con auto-refresh
    async makeAuthenticatedRequest(url, options = {}) {
        // Agregar access token al header
        const headers = {
            ...options.headers,
            'Authorization': `Bearer ${this.accessToken}`
        };

        try {
            const response = await fetch(url, { ...options, headers });
            
            // Si el token expiró, intentar refrescar
            if (response.status === 401) {
                await this.refreshAccessToken();
                
                // Reintentar la petición con el nuevo token
                headers.Authorization = `Bearer ${this.accessToken}`;
                return await fetch(url, { ...options, headers });
            }
            
            return response;
        } catch (error) {
            console.error('Error en petición autenticada:', error);
            throw error;
        }
    }

    // Logout
    logout() {
        this.accessToken = null;
        this.refreshToken = null;
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        window.location.href = '/login';
    }
}
```

### **React Hook**

```jsx
import { useState, useEffect, useCallback } from 'react';

const useAuth = () => {
    const [accessToken, setAccessToken] = useState(localStorage.getItem('accessToken'));
    const [refreshToken, setRefreshToken] = useState(localStorage.getItem('refreshToken'));
    const [isAuthenticated, setIsAuthenticated] = useState(!!accessToken);

    const login = useCallback(async (username, password) => {
        try {
            const response = await fetch('/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();
                setAccessToken(data.accessToken);
                setRefreshToken(data.refreshToken);
                localStorage.setItem('accessToken', data.accessToken);
                localStorage.setItem('refreshToken', data.refreshToken);
                setIsAuthenticated(true);
                return data;
            }
        } catch (error) {
            console.error('Error en login:', error);
            throw error;
        }
    }, []);

    const refreshAccessToken = useCallback(async () => {
        try {
            const response = await fetch('/auth/refresh', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ refreshToken })
            });

            if (response.ok) {
                const data = await response.json();
                setAccessToken(data.accessToken);
                localStorage.setItem('accessToken', data.accessToken);
                return data.accessToken;
            } else {
                logout();
                throw new Error('Refresh token expirado');
            }
        } catch (error) {
            console.error('Error refrescando token:', error);
            logout();
            throw error;
        }
    }, [refreshToken]);

    const logout = useCallback(() => {
        setAccessToken(null);
        setRefreshToken(null);
        setIsAuthenticated(false);
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
    }, []);

    return {
        accessToken,
        refreshToken,
        isAuthenticated,
        login,
        refreshAccessToken,
        logout
    };
};
```

### **Angular Service**

```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private accessToken = new BehaviorSubject<string | null>(localStorage.getItem('accessToken'));
    private refreshToken = new BehaviorSubject<string | null>(localStorage.getItem('refreshToken'));

    constructor(private http: HttpClient) {}

    login(username: string, password: string): Observable<any> {
        return this.http.post('/auth/login', { username, password }).pipe(
            catchError(this.handleError)
        );
    }

    refreshAccessToken(): Observable<string> {
        const refreshToken = this.refreshToken.value;
        if (!refreshToken) {
            return throwError('No refresh token available');
        }

        return this.http.post('/auth/refresh', { refreshToken }).pipe(
            switchMap((response: any) => {
                this.accessToken.next(response.accessToken);
                localStorage.setItem('accessToken', response.accessToken);
                return response.accessToken;
            }),
            catchError((error) => {
                this.logout();
                return throwError('Refresh token expired');
            })
        );
    }

    makeAuthenticatedRequest(url: string, options: any = {}): Observable<any> {
        const headers = new HttpHeaders({
            ...options.headers,
            'Authorization': `Bearer ${this.accessToken.value}`
        });

        return this.http.request(options.method || 'GET', url, {
            ...options,
            headers
        }).pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.status === 401) {
                    return this.refreshAccessToken().pipe(
                        switchMap(() => {
                            const newHeaders = new HttpHeaders({
                                ...options.headers,
                                'Authorization': `Bearer ${this.accessToken.value}`
                            });
                            return this.http.request(options.method || 'GET', url, {
                                ...options,
                                headers: newHeaders
                            });
                        })
                    );
                }
                return throwError(error);
            })
        );
    }

    logout(): void {
        this.accessToken.next(null);
        this.refreshToken.next(null);
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
    }

    private handleError(error: HttpErrorResponse) {
        console.error('An error occurred:', error);
        return throwError('Something went wrong');
    }
}
```

## 🛡️ Mejores Prácticas

### **1. Almacenamiento Seguro**
- **Access Token**: localStorage/sessionStorage (corta duración)
- **Refresh Token**: localStorage (larga duración)
- **Producción**: Considerar httpOnly cookies para refresh tokens

### **2. Manejo de Errores**
- **401 Unauthorized**: Intentar refresh automáticamente
- **Refresh fallido**: Redirigir al login
- **Network errors**: Reintentar con backoff exponencial

### **3. Interceptores HTTP**
```javascript
// Interceptor para axios
axios.interceptors.response.use(
    response => response,
    async error => {
        if (error.response?.status === 401) {
            try {
                await authService.refreshAccessToken();
                return axios.request(error.config);
            } catch (refreshError) {
                authService.logout();
                return Promise.reject(refreshError);
            }
        }
        return Promise.reject(error);
    }
);
```

### **4. Timeouts y Reintentos**
```javascript
// Configurar timeout para refresh
const refreshWithTimeout = async () => {
    const timeout = new Promise((_, reject) => 
        setTimeout(() => reject(new Error('Timeout')), 5000)
    );
    
    return Promise.race([
        authService.refreshAccessToken(),
        timeout
    ]);
};
```

## 🔄 Flujo Completo

### **1. Login Inicial**
```javascript
const authData = await authService.login('user', 'password');
// Guarda accessToken y refreshToken
```

### **2. Petición Autenticada**
```javascript
const response = await authService.makeAuthenticatedRequest('/api/user/profile');
// Si access token expiró, automáticamente refresca
```

### **3. Refresh Automático**
```javascript
// Se ejecuta automáticamente cuando access token expira
const newAccessToken = await authService.refreshAccessToken();
```

### **4. Logout**
```javascript
authService.logout();
// Limpia tokens y redirige al login
```

## ⚠️ Consideraciones de Seguridad

### **1. XSS Protection**
- Usar httpOnly cookies en producción
- Sanitizar datos antes de almacenar

### **2. CSRF Protection**
- Implementar tokens CSRF
- Validar origen de peticiones

### **3. Token Rotation**
- Considerar rotar refresh tokens
- Invalidar tokens antiguos

### **4. Logout Seguro**
- Invalidar tokens en el servidor
- Limpiar almacenamiento local
- Redirigir inmediatamente

## 📝 Ejemplo de Uso Completo

```javascript
// Configuración inicial
const authService = new AuthService();

// Login
try {
    await authService.login('admin', 'password');
    console.log('Login exitoso');
} catch (error) {
    console.error('Error en login:', error);
}

// Petición autenticada (con auto-refresh)
try {
    const response = await authService.makeAuthenticatedRequest('/api/user/profile');
    const userData = await response.json();
    console.log('Datos del usuario:', userData);
} catch (error) {
    console.error('Error obteniendo perfil:', error);
}

// Logout
authService.logout();
```

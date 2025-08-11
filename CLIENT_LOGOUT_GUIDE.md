# Guía de Implementación de Logout en el Cliente

## 📋 Descripción

Esta guía explica cómo implementar el logout en el lado del cliente para invalidar tokens JWT de manera segura.

## 🔐 Endpoint de Logout

### **POST /auth/logout**
- **Headers**: `Authorization: Bearer <token>`
- **Respuesta**: `"Logout exitoso. El token ha sido invalidado en el servidor."`

## 💻 Implementación en el Cliente

### **JavaScript/TypeScript**

```javascript
// Función básica de logout
function logout() {
    // 1. Eliminar token del almacenamiento local
    localStorage.removeItem("token");
    sessionStorage.removeItem("token");
    
    // 2. Limpiar cualquier estado de autenticación
    clearAuthState();
    
    // 3. Redirigir al login
    window.location.href = "/login";
}

// Función de logout con llamada al servidor
async function logoutWithServer() {
    try {
        const token = localStorage.getItem("token");
        
        if (token) {
            // Llamar al endpoint de logout
            const response = await fetch('/auth/logout', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                console.log('Logout exitoso en el servidor');
            }
        }
    } catch (error) {
        console.error('Error en logout:', error);
    } finally {
        // Siempre limpiar el cliente, incluso si falla la llamada al servidor
        logout();
    }
}

// Función para limpiar estado de autenticación
function clearAuthState() {
    // Eliminar token
    localStorage.removeItem("token");
    sessionStorage.removeItem("token");
    
    // Limpiar headers por defecto
    delete axios.defaults.headers.common['Authorization'];
    
    // Limpiar estado de la aplicación
    if (window.authStore) {
        window.authStore.clear();
    }
}
```

### **React**

```jsx
import { useNavigate } from 'react-router-dom';

const useAuth = () => {
    const navigate = useNavigate();
    
    const logout = async () => {
        try {
            const token = localStorage.getItem('token');
            
            if (token) {
                await fetch('/auth/logout', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
            }
        } catch (error) {
            console.error('Error en logout:', error);
        } finally {
            // Limpiar estado local
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            
            // Redirigir al login
            navigate('/login');
        }
    };
    
    return { logout };
};
```

### **Angular**

```typescript
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    
    constructor(
        private http: HttpClient,
        private router: Router
    ) {}
    
    async logout(): Promise<void> {
        try {
            const token = localStorage.getItem('token');
            
            if (token) {
                const headers = new HttpHeaders({
                    'Authorization': `Bearer ${token}`
                });
                
                await this.http.post('/auth/logout', {}, { headers }).toPromise();
            }
        } catch (error) {
            console.error('Error en logout:', error);
        } finally {
            // Limpiar estado local
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            
            // Redirigir al login
            this.router.navigate(['/login']);
        }
    }
}
```

### **Vue.js**

```javascript
// store/auth.js
export default {
    state: {
        token: localStorage.getItem('token') || null,
        user: JSON.parse(localStorage.getItem('user') || 'null')
    },
    
    mutations: {
        clearAuth(state) {
            state.token = null;
            state.user = null;
            localStorage.removeItem('token');
            localStorage.removeItem('user');
        }
    },
    
    actions: {
        async logout({ commit }) {
            try {
                const token = localStorage.getItem('token');
                
                if (token) {
                    await fetch('/auth/logout', {
                        method: 'POST',
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json'
                        }
                    });
                }
            } catch (error) {
                console.error('Error en logout:', error);
            } finally {
                commit('clearAuth');
                this.$router.push('/login');
            }
        }
    }
};
```

## 🛡️ Mejores Prácticas

### **1. Limpieza Completa**
- Eliminar token de localStorage/sessionStorage
- Limpiar headers de autenticación
- Limpiar estado de la aplicación
- Limpiar cookies relacionadas

### **2. Manejo de Errores**
- Siempre limpiar el cliente, incluso si falla la llamada al servidor
- Mostrar mensajes de error apropiados
- Logging para debugging

### **3. Seguridad**
- Llamar al endpoint de logout para invalidar el token en el servidor
- Usar HTTPS en producción
- Implementar timeout de sesión

### **4. UX**
- Mostrar indicador de carga durante el logout
- Redirigir automáticamente al login
- Limpiar formularios y datos sensibles

## 🔄 Flujo de Logout

1. **Usuario hace clic en "Logout"**
2. **Cliente llama a `/auth/logout`** (opcional pero recomendado)
3. **Servidor invalida el token** (lo agrega a la lista negra)
4. **Cliente limpia el almacenamiento local**
5. **Cliente redirige al login**

## ⚠️ Consideraciones

- **Tokens expirados**: Los tokens expirados automáticamente no son válidos
- **Lista negra**: Los tokens en la lista negra son rechazados inmediatamente
- **Múltiples pestañas**: Considerar sincronización entre pestañas
- **Offline**: El logout debe funcionar incluso sin conexión al servidor

## 📝 Ejemplo de Uso

```javascript
// En tu aplicación
document.getElementById('logout-btn').addEventListener('click', async () => {
    try {
        await logoutWithServer();
        showMessage('Sesión cerrada exitosamente');
    } catch (error) {
        showError('Error al cerrar sesión');
    }
});
```

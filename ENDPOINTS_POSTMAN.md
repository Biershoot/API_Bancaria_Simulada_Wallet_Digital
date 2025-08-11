# Endpoints API Wallet para Postman

## **Base URL**: `http://localhost:8080`

## **Configuración de Postman**

### Variables de Entorno (recomendado):
1. Crea una nueva colección en Postman
2. Ve a "Variables" y agrega:
   - `base_url`: `http://localhost:8080`
   - `token`: (se llenará automáticamente después del login)

---

## **1. Autenticación JWT**

### **1.1 Login de Usuario**
- **URL**: `POST {{base_url}}/auth/login`
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (JSON):
```json
{
    "username": "admin",
    "password": "password"
}
```
- **Respuesta esperada**:
```json
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
    "message": "Login exitoso"
}
```

### **1.2 Test de Autenticación**
- **URL**: `GET {{base_url}}/auth/test`
- **Headers**: 
  - `Content-Type: application/json`
- **Respuesta esperada**:
```json
"Endpoint de autenticación funcionando correctamente"
```

### **1.3 Refresh Token**
- **URL**: `POST {{base_url}}/auth/refresh`
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (JSON):
```json
{
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```
- **Respuesta esperada**:
```json
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
    "message": "Token refrescado exitosamente"
}
```

### **1.4 Logout**
- **URL**: `POST {{base_url}}/auth/logout`
- **Headers**: 
  - `Authorization: Bearer {{token}}`
- **Respuesta esperada**:
```json
"Logout exitoso. El token ha sido invalidado en el servidor."
```

### **Usuarios Disponibles para Pruebas**:
- **Usuario**: `admin` / **Contraseña**: `password` (Roles: ADMIN, USER)
- **Usuario**: `user` / **Contraseña**: `password` (Roles: USER)

---
## **2. Gestión de Usuario (USER y ADMIN)**

### **2.1 Perfil de Usuario**
- **URL**: `GET {{base_url}}/api/user/profile`
- **Headers**: 
  - `Authorization: Bearer {{token}}`
- **Respuesta esperada**:
```json
"Perfil del usuario: admin"
```

### **2.2 Actualizar Perfil**
- **URL**: `PUT {{base_url}}/api/user/profile`
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer {{token}}`
- **Body** (JSON):
```json
{
    "name": "Nuevo Nombre",
    "email": "nuevo@email.com"
}
```
- **Respuesta esperada**:
```json
"Perfil actualizado exitosamente"
```

### **2.3 Preferencias de Usuario**
- **URL**: `GET {{base_url}}/api/user/preferences`
- **Headers**: 
  - `Authorization: Bearer {{token}}`
- **Respuesta esperada**:
```json
"Preferencias del usuario"
```

### **2.4 Actividad del Usuario**
- **URL**: `GET {{base_url}}/api/user/activity`
- **Headers**: 
  - `Authorization: Bearer {{token}}`
- **Respuesta esperada**:
```json
"Historial de actividad del usuario"
```

---
## **3. Administración (Solo ADMIN)**

### **3.1 Dashboard de Administración**
- **URL**: `GET {{base_url}}/api/admin/dashboard`
- **Headers**: 
  - `Authorization: Bearer {{token}}` (debe ser token de admin)
- **Respuesta esperada**:
```json
"Panel de administración - Solo accesible para administradores"
```

### **3.2 Listar Usuarios**
- **URL**: `GET {{base_url}}/api/admin/users`
- **Headers**: 
  - `Authorization: Bearer {{token}}` (debe ser token de admin)
- **Respuesta esperada**:
```json
"Lista de usuarios del sistema"
```

### **3.3 Deshabilitar Usuario**
- **URL**: `POST {{base_url}}/api/admin/users/{userId}/disable`
- **Headers**: 
  - `Authorization: Bearer {{token}}` (debe ser token de admin)
- **Respuesta esperada**:
```json
"Usuario 123 deshabilitado exitosamente"
```

### **3.4 Estadísticas del Sistema**
- **URL**: `GET {{base_url}}/api/admin/statistics`
- **Headers**: 
  - `Authorization: Bearer {{token}}` (debe ser token de admin)
- **Respuesta esperada**:
```json
"Estadísticas del sistema - Total de usuarios: 150, Transacciones: 1250"
```

---
## **4. Gestión de Wallet**

### **2.1 Crear Wallet**
- **URL**: `POST {{base_url}}/api/wallet/create`
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer {{token}}`
- **Body**: No requiere body
- **Respuesta esperada**:
```json
{
    "id": 1,
    "userEmail": "juan@ejemplo.com",
    "balance": 0.00
}
```

### **2.2 Consultar Balance**
- **URL**: `GET {{base_url}}/api/wallet/balance`
- **Headers**: 
  - `Authorization: Bearer {{token}}`
- **Respuesta esperada**:
```json
{
    "id": 1,
    "userEmail": "juan@ejemplo.com",
    "balance": 1000.00
}
```

### **2.3 Realizar Transferencia**
- **URL**: `POST {{base_url}}/api/wallet/transfer`
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer {{token}}`
- **Body** (JSON):
```json
{
    "toEmail": "maria@ejemplo.com",
    "amount": 500.00
}
```
- **Respuesta esperada**:
```json
{
    "message": "Transferencia realizada con éxito",
    "fromEmail": "juan@ejemplo.com",
    "toEmail": "maria@ejemplo.com",
    "amount": 500.00,
    "timestamp": "2024-01-15T10:30:00Z"
}
```

---
## **5. Email y Notificaciones**

### **3.1 Enviar Correo de Prueba**
- **URL**: `POST {{base_url}}/api/email/test`
- **Headers**: 
  - `Content-Type: application/x-www-form-urlencoded`
- **Body** (form-data):
  - `email`: `tu_correo@ejemplo.com`
- **Respuesta esperada**:
```json
"Correo de prueba enviado exitosamente a tu_correo@ejemplo.com"
```

### **3.2 Simular Notificación de Transferencia**
- **URL**: `POST {{base_url}}/api/email/test-transfer`
- **Headers**: 
  - `Content-Type: application/x-www-form-urlencoded`
- **Body** (form-data):
  - `emailDestinatario`: `destinatario@ejemplo.com`
  - `emailRemitente`: `remitente@ejemplo.com`
  - `monto`: `500.00`
- **Respuesta esperada**:
```json
"Notificación de transferencia enviada exitosamente"
```

---
## **6. Documentación API**

### **6.1 Swagger UI**
- **URL**: `GET {{base_url}}/swagger-ui.html`
- **Descripción**: Interfaz web para explorar y probar la API

### **6.2 Especificación OpenAPI**
- **URL**: `GET {{base_url}}/v3/api-docs`
- **Descripción**: Especificación JSON de la API

---

## **Flujo de Prueba Recomendado**

### **Paso 1: Login y obtener tokens**
1. Login usuario admin: `POST /auth/login` con `{"username": "admin", "password": "password"}`
2. Login usuario user: `POST /auth/login` con `{"username": "user", "password": "password"}`
3. Guardar tokens en variables de entorno

### **Paso 2: Probar roles de usuario**
1. Probar endpoints de usuario con token de admin: `GET /api/user/profile`
2. Probar endpoints de usuario con token de user: `GET /api/user/profile`

### **Paso 3: Probar roles de administración**
1. Probar endpoints de admin con token de admin: `GET /api/admin/dashboard`
2. Probar endpoints de admin con token de user (debe fallar): `GET /api/admin/dashboard`

### **Paso 4: Crear wallets**
1. Crear wallet para usuario 1: `POST /api/wallet/create`
2. Crear wallet para usuario 2: `POST /api/wallet/create`

### **Paso 5: Probar transferencias**
1. Consultar balance usuario 1: `GET /api/wallet/balance`
2. Realizar transferencia: `POST /api/wallet/transfer`
3. Verificar balances actualizados

---

## **Configuración de Base de Datos**

### **MySQL**
- **Host**: `localhost`
- **Puerto**: `3306`
- **Base de datos**: `wallet_db`
- **Usuario**: `root`
- **Contraseña**: `root`

### **Configuración automática**
La aplicación creará automáticamente:
- Base de datos si no existe
- Tablas necesarias
- Roles por defecto (USER, ADMIN)

---

## **Notas Importantes**

### **Seguridad**
- Los endpoints de autenticación (`/auth/**`) están permitidos sin token
- Los endpoints de email (`/api/email/**`) están permitidos sin token para pruebas
- Los endpoints de wallet (`/api/wallet/**`) requieren token JWT válido
- Los endpoints de usuario (`/api/user/**`) requieren rol USER o ADMIN
- Los endpoints de administración (`/api/admin/**`) requieren rol ADMIN
- **Access Token**: Expira en 15 minutos (seguridad)
- **Refresh Token**: Expira en 7 días (conveniencia)
- Swagger UI está disponible sin autenticación para facilitar las pruebas

### **Validaciones**
- Un usuario solo puede tener una wallet
- Las transferencias requieren fondos suficientes
- Los montos deben ser mayores a 0
- Los emails deben existir en el sistema

### **Manejo de Errores**
- **400**: Datos inválidos
- **401**: No autorizado (token inválido o faltante)
- **404**: Recurso no encontrado
- **500**: Error interno del servidor

---

## **Ejemplo de Colección Postman**

### **Variables de Colección**:
```json
{
    "base_url": "http://localhost:8080",
    "token": "",
    "user1_email": "juan@ejemplo.com",
    "user2_email": "maria@ejemplo.com"
}
```

### **Script de Post-Request para Login**:
```javascript
// En el endpoint de login, agregar este script en "Tests"
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.collectionVariables.set("token", response.token);
}
```


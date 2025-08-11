# Endpoints API Wallet para Postman

## **Base URL**: `http://localhost:8080`

## **Configuración de Postman**

### Variables de Entorno (recomendado):
1. Crea una nueva colección en Postman
2. Ve a "Variables" y agrega:
   - `base_url`: `http://localhost:8080`
   - `token`: (se llenará automáticamente después del login)

---

## **1. Autenticación**

### **1.1 Registro de Usuario**
- **URL**: `POST {{base_url}}/api/auth/register`
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (JSON):
```json
{
    "name": "Juan Pérez",
    "email": "juan@ejemplo.com",
    "password": "123456"
}
```
- **Respuesta esperada**:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### **1.2 Login de Usuario**
- **URL**: `POST {{base_url}}/api/auth/login`
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (JSON):
```json
{
    "email": "juan@ejemplo.com",
    "password": "123456"
}
```
- **Respuesta esperada**:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## **2. Gestión de Wallet**

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

## **3. Documentación API**

### **3.1 Swagger UI**
- **URL**: `GET {{base_url}}/swagger-ui.html`
- **Descripción**: Interfaz web para explorar y probar la API

### **3.2 Especificación OpenAPI**
- **URL**: `GET {{base_url}}/v3/api-docs`
- **Descripción**: Especificación JSON de la API

---

## **Flujo de Prueba Recomendado**

### **Paso 1: Registrar usuarios**
1. Registrar usuario 1: `POST /api/auth/register`
2. Registrar usuario 2: `POST /api/auth/register`

### **Paso 2: Login y obtener tokens**
1. Login usuario 1: `POST /api/auth/login`
2. Login usuario 2: `POST /api/auth/login`
3. Guardar tokens en variables de entorno

### **Paso 3: Crear wallets**
1. Crear wallet para usuario 1: `POST /api/wallet/create`
2. Crear wallet para usuario 2: `POST /api/wallet/create`

### **Paso 4: Probar transferencias**
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
- Los endpoints de autenticación (`/api/auth/**`) están permitidos sin token
- Los endpoints de wallet (`/api/wallet/**`) requieren token JWT válido
- El token JWT expira en 24 horas

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


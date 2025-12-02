# Cargador - proyecto de ejemplo

Este proyecto es una versión con temática "cargador de celular" que replica la funcionalidad básica del proyecto de referencia (endpoints CRUD) pero manteniéndose como un proyecto independiente.

Cómo correr:

1. Abrir una terminal en `c:\Users\PC\Desktop\examen\cargador\cargador`
2. Ejecutar (Windows PowerShell):

```powershell
.\gradlew.bat bootRun
```

El servicio se levantará en http://localhost:8081 y el H2 Console estará en http://localhost:8081/h2-console

Endpoints principales:
- GET /cargadores  -> listar
- POST /cargadores -> crear (payload sin "celular"; usar query ?celularId= para asociar)
- GET /cargadores/{id} -> obtener
- PUT /cargadores/{id} -> reemplazar
- PATCH /cargadores/{id} -> actualizar parcialmente
- DELETE /cargadores/{id} -> eliminar
- POST /celulares -> crear (payload sin lista de cargadores; usar query ?cargadorIds=1,2 para asociar)
"# backend_final" 

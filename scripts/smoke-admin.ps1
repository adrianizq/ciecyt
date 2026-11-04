param(
    [string]$BaseUrl = "http://localhost:8080"
)

$ErrorActionPreference = "Stop"
$fallas = @()

function Login([string]$usuario, [string]$clave) {
    $body = @{ username = $usuario; password = $clave; rememberMe = $true } | ConvertTo-Json
    return (Invoke-RestMethod -Method Post -Uri "$BaseUrl/api/authenticate" -ContentType "application/json" -Body $body).id_token
}

function Probar([string]$nombre, [scriptblock]$accion) {
    try {
        & $accion | Out-Null
        Write-Host "[OK] $nombre" -ForegroundColor Green
    } catch {
        Write-Host "[FALLO] $nombre -> $($_.Exception.Message)" -ForegroundColor Red
        $script:fallas += $nombre
    }
}

function Estado([string]$metodo, [string]$ruta, [hashtable]$headers, [string]$cuerpo = $null) {
    try {
        $respuesta = Invoke-WebRequest -UseBasicParsing -Method $metodo -Uri "$BaseUrl$ruta" -Headers $headers -ContentType "application/json" -Body $cuerpo
        return [int]$respuesta.StatusCode
    } catch {
        if ($_.Exception.Response) {
            return [int]$_.Exception.Response.StatusCode
        }
        throw
    }
}

Probar "health" {
    $estado = (Invoke-WebRequest -UseBasicParsing -Uri "$BaseUrl/management/health").StatusCode
    if ($estado -ne 200) { throw "health=$estado" }
}

$tokenAdmin = Login "admin" "admin"
$admin = @{ Authorization = "Bearer $tokenAdmin" }
$tokenEstudiante = Login "keliun" "keliun"
$estudiante = @{ Authorization = "Bearer $tokenEstudiante" }

Probar "periodos" {
    Invoke-RestMethod -Method Get -Uri "$BaseUrl/api/periodos" -Headers $admin | Out-Null
}
Probar "festivos" {
    Invoke-RestMethod -Method Get -Uri "$BaseUrl/api/festivos" -Headers $admin | Out-Null
}
Probar "ciclos con modalidades" {
    $ciclos = Invoke-RestMethod -Method Get -Uri "$BaseUrl/api/ciclos/all" -Headers $admin
    if (@($ciclos).Count -eq 0) { throw "sin ciclos" }
    if ($null -eq @($ciclos)[0].modalidadIds) { throw "sin modalidadIds" }
}
Probar "usuarios" {
    Invoke-RestMethod -Method Get -Uri "$BaseUrl/api/users_nopage" -Headers $admin | Out-Null
}
Probar "proyectos" {
    Invoke-RestMethod -Method Get -Uri "$BaseUrl/api/proyectosIntegrantes" -Headers $admin | Out-Null
}
Probar "menus admin" {
    $menus = Invoke-RestMethod -Method Get -Uri "$BaseUrl/api/menus-roles/ADMIN" -Headers $admin
    if (@($menus | Where-Object { $_.id -eq 7025 }).Count -eq 0) { throw "falta menu 7025" }
}
Probar "estudiante no puede crear festivos" {
    $estado = Estado "Post" "/api/festivos" $estudiante '{"fecha":"2026-12-25","descripcion":"x","activo":true}'
    if ($estado -ne 403) { throw "esperado 403, recibido $estado" }
}
Probar "asesores externos" {
    Invoke-RestMethod -Method Get -Uri "$BaseUrl/api/asesores-externos/facultad/30001" -Headers $admin | Out-Null
}

if ($fallas.Count -gt 0) {
    Write-Host "`nFallaron $($fallas.Count) pruebas: $($fallas -join ', ')" -ForegroundColor Red
    exit 1
}
Write-Host "`nSmoke admin completado correctamente." -ForegroundColor Green
exit 0

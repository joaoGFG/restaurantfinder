# Restaurant Finder - Script de Teste Rápido

Write-Host "`n=== Restaurant Finder - Teste ===" -ForegroundColor Green

# URLs dos serviços
$RESTAURANT_URL = "http://localhost:8081/api/restaurants"
$USER_URL = "http://localhost:8082/api/users"
$RECOMMENDATION_URL = "http://localhost:8083/api/recommendations"

Write-Host "`nAguardando serviços inicializarem..." -ForegroundColor Yellow
Start-Sleep -Seconds 3

# Criar restaurante
Write-Host "`n[1/4] Criando restaurante..." -ForegroundColor Cyan
$restaurant = @{
    name = "Bella Italia"
    category = "Italiana"
    location = "Centro"
    rating = 4.5
    cuisine = "Italiana"
    priceRange = 80.0
    description = "Autêntica culinária italiana"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri $RESTAURANT_URL -Method POST -ContentType "application/json" -Body $restaurant
    Write-Host "✓ Restaurante criado com ID: $($response.id)" -ForegroundColor Green
}
catch {
    Write-Host "✗ Erro: $_" -ForegroundColor Red
    exit 1
}

# Criar usuário
Write-Host "`n[2/4] Criando usuário..." -ForegroundColor Cyan
$user = @{
    email = "joao@example.com"
    name = "João Silva"
    location = "Centro"
    preferredCuisines = @("Italiana")
    preferredCategories = @("Restaurante")
    maxPriceRange = 120.0
    minRating = 4.0
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri $USER_URL -Method POST -ContentType "application/json" -Body $user
    Write-Host "✓ Usuário criado com ID: $($response.id)" -ForegroundColor Green
}
catch {
    Write-Host "✗ Erro: $_" -ForegroundColor Red
    exit 1
}

# Listar restaurantes
Write-Host "`n[3/4] Listando restaurantes..." -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri $RESTAURANT_URL -Method GET
    Write-Host "✓ Total de restaurantes: $($response.Count)" -ForegroundColor Green
}
catch {
    Write-Host "✗ Erro: $_" -ForegroundColor Red
    exit 1
}

# Obter recomendações
Write-Host "`n[4/4] Obtendo recomendações..." -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$RECOMMENDATION_URL/user/1" -Method GET
    Write-Host "✓ Recomendações obtidas!" -ForegroundColor Green
    Write-Host "  Usuário: $($response.userId)" -ForegroundColor White
    Write-Host "  Restaurantes recomendados: $($response.recommendedRestaurants.Count)" -ForegroundColor White
    
    if ($response.recommendedRestaurants.Count -gt 0) {
        Write-Host "  Primeiro: $($response.recommendedRestaurants[0].name)" -ForegroundColor White
    }
    
    Write-Host "`n  IA Reasoning:`n  $($response.aiReasoning)" -ForegroundColor Yellow
}
catch {
    Write-Host "✗ Erro: $_" -ForegroundColor Red
    exit 1
}

Write-Host "`n=== ✅ Todos os testes passaram! ===" -ForegroundColor Green
Write-Host "Dashboard Eureka: http://localhost:8761" -ForegroundColor Cyan
Write-Host "RabbitMQ: http://localhost:15672 (guest:guest)" -ForegroundColor Cyan

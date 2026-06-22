# 🍳 TryCook API - Backend pro správu receptů

Tento projekt je semestrální prací zaměřenou na tvorbu REST API pro správu kuchařských receptů. Aplikace je postavena na moderních principech, obsahuje zabezpečení pomocí JWT tokenů, uživatelské role a plně interaktivní dokumentaci.

## 🚀 Použité technologie
* **Jazyk:** Java (přes Spring Boot framework)
* **Bezpečnost:** Spring Security + JWT (JSON Web Tokens)
* **Databáze:** H2 Database (in-memory) + Spring Data JPA
* **Dokumentace:** Swagger / OpenAPI 3
* **Testování:** JUnit 5 + Mockito

## ⚙️ Architektura a vrstvy
Aplikace striktně dodržuje vrstvenou architekturu:
* **Controllers:** Zpracovávají HTTP požadavky a vrací odpovědi (např. `RecipeController`).
* **Services:** Obsahují byznys logiku a komunikují s repozitáři.
* **Repositories:** Zajišťují komunikaci s databází.
* **Entities & DTOs:** Bezpečné oddělení databázových modelů od dat, která se posílají ven (včetně vlastních validátorů, např. `@ValidIngredients`).

## 🔐 Bezpečnost a role
Aplikace rozlišuje dvě základní úrovně přístupu:
1. **Veřejný přístup:** Zobrazení dokumentace a možnost přihlášení.
2. **Přihlášený uživatel (ROLE_USER / ROLE_ADMIN):** Možnost vytvářet a upravovat recepty. Citlivé operace (jako mazání receptů) jsou vyhrazeny výhradně pro roli `ADMIN`.

## 🛠️ Jak aplikaci spustit
1. Naklonujte si tento repozitář do počítače.
2. Otevřete projekt ve svém IDE (IntelliJ IDEA / Eclipse).
3. Spusťte třídu `TryCookApplication.java`.
4. Aplikace nastartuje na portu `8080`.

## 📚 Jak testovat API (Swagger)
Po spuštění aplikace otevřete webový prohlížeč a přejděte na adresu:
**http://localhost:8080/swagger-ui/index.html**

Pro přístup k chráněným endpointům použijte testovacího uživatele, který se automaticky generuje při startu aplikace:
* **Username:** `karolina_studentka`
* **Password:** `tajneheslo123`

Vygenerovaný JWT token z endpointu `/api/auth/login` následně vložte do autorizačního okna (tlačítko "Authorize" vpravo nahoře) s prefixem `Bearer ` (Swagger si prefix přidává automaticky, stačí vložit čistý token).

# Setup du projet

Le projet est composé de deux parties :

- un **backend Spring Boot** (port 8080)
- un **frontend Node.js** (port 8081)

Les deux doivent tourner en même temps pour que l’application fonctionne.

---

##  Prérequis

Assurez-vous d’avoir installé :

- Java
- Maven
- Node.js
- npm

Vous pouvez vérifier avec :

```bash
java -version
mvn -version
node -v
npm -v
```

## Lancer l’application

Il faut ouvrir deux terminaux.
1 — Lancer le backend

```
cd ./back
mvn test
mvn spring-boot:run
```

Le backend sera accessible sur :

http://localhost:8080

et la documentation swagger à :
http://localhost:8080/swagger-ui/index.html#/

2 — Lancer le frontend

Ouvrir un second terminal :

```
cd ./front
npm install
npm run dev
```

Le frontend sera accessible sur :

http://localhost:8081

🌐 Accès à l’application

Une fois les deux serveurs lancés :

http://localhost:8081

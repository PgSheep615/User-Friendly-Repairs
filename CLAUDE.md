# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

User-Friendly-Repairs is a WeChat Mini Program backend for a repair order management system. It's a monolithic Spring Boot application built for a WeChat Mini Program development competition.

**Technology Stack:**
- Spring Boot 2.7.3
- Spring Security (JWT authentication)
- MyBatis-Plus 3.5.3.1 (ORM)
- MySQL (database)
- Redis (caching)
- Aliyun OSS (file upload)
- Druid (connection pool)
- Knife4j (API documentation/Swagger)

## Build and Run

### Important: Multi-Module Maven Project

This is a Maven multi-module project with the following structure:
- **repair-common**: Common utilities, constants, exceptions, properties
- **repair-pojo**: Data transfer objects (DTOs, entities, VOs)
- **repair-server**: Main application module (depends on common and pojo)

### Build Commands

**Always build from the project root directory**, not from individual modules:

```bash
# From project root (/User-Friendly-Repairs)
mvn clean install
```

**If using JDK 21 from command line but project requires JDK 8:**
```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home mvn clean install
```

**To build without tests:**
```bash
mvn clean install -DskipTests
```

**To build only the server module (after dependencies are installed):**
```bash
mvn clean package -pl repair-server
```

### Run the Application

The main application class is `RepairApplication` in the `repair-server` module.

```bash
# After building, run from repair-server/target
java -jar repair-server/target/repair-server-1.0-SNAPSHOT.jar
```

**Server Port:** 10010 (configured in application.yml)

### Common Build Issues

1. **Missing dependency errors for repair-common or repair-pojo**: You must build from the root directory, not from repair-server
2. **Lombok compilation errors**: Ensure JDK 8 is used (current Lombok version 1.18.24 is compatible with JDK 8)

## Architecture

### Module Dependencies

```
repair-server (main app)
    ├── repair-common (utilities, exceptions, constants)
    └── repair-pojo (entities, DTOs, VOs)
```

### Code Organization

**repair-server** follows standard Spring Boot layered architecture:
- `controller/`: REST API endpoints
  - `admin/`: Admin management endpoints
  - `user/`: User-facing endpoints
  - `order/`: Repair order endpoints
- `service/`: Business logic layer
- `mapper/`: MyBatis-Plus data access layer
- `config/`: Spring configuration classes
- `filter/`: Security filters
- `interceptor/`: Request interceptors
- `aspect/`: AOP aspects
- `annotation/`: Custom annotations

**repair-common** contains:
- `constant/`: Application constants
- `context/`: Thread-local context utilities
- `exception/`: Custom exception classes
- `properties/`: Configuration properties (JWT, AliOss, WeChat)
- `result/`: Standard API response wrappers
- `utils/`: Utility classes

**repair-pojo** contains:
- `entity/`: Database entities (User, Admin, RepairOrder, Feedback)
- `dto/`: Data transfer objects for requests
- `vo/`: View objects for responses

### Security Architecture

The application uses Spring Security with JWT authentication:
- JWT token name: `token` (header)
- Token TTL: 7200000ms (2 hours)
- Security filters handle authentication/authorization
- User secret key configured in application.yml

### Database

Database schema is in `database.sql` at project root.

**Main tables:**
- `user`: User information with WeChat OpenID
- `admin`: Admin users with group assignments and scores
- `repair_order`: Repair order records
- `feedback`: User feedback

All tables include soft delete (`is_deleted`) and optimistic locking (`version`) fields.

## Configuration

### Environment Setup

Configuration uses Spring profiles with variables substitution:
- Active profile: `dev` (in application.yml)
- Environment-specific config: `application-dev.yml`

**Required environment variables/properties in application-dev.yml:**
- Database: `repair.datasource.{host,port,database,username,password,driver-class-name}`
- Redis: `repair.redis.{host,port,password,database}`
- Aliyun OSS: `repair.alioss.{endpoint,access-key-id,access-key-secret,bucket-name}`
- WeChat: `repair.wechat.{appid,secret}`

### MyBatis-Plus Configuration

- Mapper XML location: `classpath*:/mapper/**/*.xml`
- Entity package: `com.repair.entity`
- ID strategy: `ASSIGN_ID` (snowflake algorithm)
- Camel case mapping: enabled
- SQL logging: stdout (for development)

## Development Notes

### Working with Dependencies

When modifying code in `repair-common` or `repair-pojo`:
1. Make your changes
2. Run `mvn clean install` from project root to rebuild all modules
3. The updated dependencies will be available to `repair-server`

### API Documentation

Knife4j (Swagger) is configured for API documentation. After starting the server, access at:
```
http://localhost:10010/doc.html
```

### Database Initialization

Run the SQL script at project root to set up the database:
```bash
mysql -u username -p database_name < database.sql
```

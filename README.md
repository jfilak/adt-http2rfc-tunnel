# ABAP ADT HTTP to RFC Tunnel

[![Build](https://github.com/jfilak/adt-http2rfc-tunnel/actions/workflows/ci.yml/badge.svg)](https://github.com/jfilak/adt-http2rfc-tunnel/actions/workflows/ci.yml)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://adoptium.net/)

A thin HTTP server that accepts SAP ABAP Development Tools (ADT) HTTP endpoint
requests and forwards them to the corresponding ADT RFC function modules via
the SAP Java Connector (JCo).

## Why

ADT clients (Eclipse, ABAP Development Tools) speak HTTP to the ABAP application
server. Some integration scenarios — sandboxed environments, legacy systems
without the HTTP whitelist enabled, or test harnesses — only have RFC access.
This tunnel bridges that gap.

## Requirements

- **Java 21** or newer (JDK, not JRE)
- **Maven 3.9+**
- **SAP Java Connector (JCo) 3.1.x** — must be downloaded from the SAP Support
  Portal; not redistributable. See [Installing SAP JCo](#installing-sap-jco).

## Installing SAP JCo

SAP JCo is not available on Maven Central. Download the archive matching your
platform from the [SAP Support Portal](https://support.sap.com/en/product/connectors/jco.html),
extract it, and install the JAR into your local Maven repository:

```bash
mvn install:install-file \
    -Dfile=sapjco3.jar \
    -DgroupId=com.sap.conn.jco \
    -DartifactId=sapjco3 \
    -Dversion=3.1.10 \
    -Dpackaging=jar
```

The native library (`libsapjco3.so` / `sapjco3.dll` / `libsapjco3.dylib`) must
be on `java.library.path` at runtime, e.g. by exporting `LD_LIBRARY_PATH` on
Linux.

## Build

```bash
mvn clean verify
```

The shaded runnable JAR is produced at
`target/adt-http2rfc-tunnel-<version>.jar`.

## Run

```bash
HTTP2RFC_PORT=8080 java -jar target/adt-http2rfc-tunnel-0.1.0-SNAPSHOT.jar
```

Verify the server is up:

```bash
curl http://localhost:8080/health
# {"status":"UP"}
```

## Configuration

| Variable         | Default | Description                          |
|------------------|---------|--------------------------------------|
| `HTTP2RFC_PORT`  | `8080`  | TCP port the HTTP server listens on. |

SAP connection parameters are read by JCo from a standard destination file or
provided via a `DestinationDataProvider`. See SAP JCo documentation for
details.

## Project layout

```
.
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/io/github/jfilak/adt/http2rfc/
│   │   └── resources/
│   └── test/
│       ├── java/io/github/jfilak/adt/http2rfc/
│       └── resources/
└── .github/
    ├── workflows/      # CI and CodeQL
    ├── ISSUE_TEMPLATE/
    └── dependabot.yml
```

## Contributing

Contributions are welcome. See [CONTRIBUTING.md](CONTRIBUTING.md) and the
[Code of Conduct](CODE_OF_CONDUCT.md).

## License

Licensed under the [Apache License, Version 2.0](LICENSE).

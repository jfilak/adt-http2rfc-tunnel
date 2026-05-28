# Contributing

Thank you for your interest in contributing to **adt-http2rfc-tunnel**! This
document describes the workflow for proposing and submitting changes.

## Getting started

1. Fork the repository and create a topic branch off `main`.
2. Install prerequisites:
   - JDK 21+
   - Maven 3.9+
   - SAP Java Connector (JCo) installed into your local Maven repository
     (see [README — Installing SAP JCo](README.md#installing-sap-jco)).
3. Build and run the tests:
   ```bash
   mvn clean verify
   ```

## How to propose a change

- **Bug reports** and **feature requests**: open a GitHub issue using the
  appropriate template.
- **Code changes**: open a pull request referencing the issue it addresses.

## Pull request checklist

- [ ] The change is covered by automated tests where reasonable.
- [ ] `mvn verify` passes locally.
- [ ] Public API changes are documented in `CHANGELOG.md` under `[Unreleased]`.
- [ ] The commit history is clean (squash WIP commits if appropriate).
- [ ] Commit messages follow [Conventional Commits](https://www.conventionalcommits.org/)
      (e.g. `feat: add /transports endpoint`, `fix: handle empty RFC table`).

## Coding conventions

- Follow the conventions enforced by `.editorconfig` (4-space indent for Java,
  2-space for YAML/JSON/Markdown, LF line endings, UTF-8).
- Prefer immutable data carriers (`record`) and `final` fields.
- Keep dependencies minimal; the project deliberately stays lightweight.
- Logging: SLF4J only — no `System.out.println`.

## Reporting security issues

Please **do not** open public GitHub issues for security vulnerabilities.
Instead, report them privately via GitHub's "Report a vulnerability" feature
(Security tab on the repository).

## Code of conduct

By participating, you agree to abide by the project's
[Code of Conduct](CODE_OF_CONDUCT.md).

## License

By contributing, you agree that your contributions will be licensed under the
[Apache License 2.0](LICENSE).

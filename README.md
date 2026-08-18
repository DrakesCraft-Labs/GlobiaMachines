<p align="center">
  <img src="https://raw.githubusercontent.com/DrakesCraft-Labs/GlobiaMachines/main/docs/banner.svg" alt="GlobiaMachines banner" width="100%">
</p>

# GlobiaMachines

GlobiaMachines is a maintained Slimefun addon for the DrakesCraft 1.21.11 ecosystem. It restores the Budget Dust Fabricator as a compact early-game machine while preserving its original Slimefun item ID and stored-block compatibility.

## Features

- Budget Dust Fabricator with an energy-backed processing cycle.
- Native integration with the DrakesCraft Slimefun core namespace.
- Dedicated nested guide category and machine subgroup.
- Spanish in-game naming for the DrakesCraft community.
- Stable `GLOBIA_BUDGET_DUST_FABRICATOR` identity for existing worlds.

## Compatibility

| Component | Target |
| --- | --- |
| Minecraft | 1.21.11 |
| Server | Paper / Purpur |
| Java | 21 |
| Slimefun | DrakesCraft maintained core |

This repository is maintained independently by DrakesCraft-Labs. It is not a fork dependency and does not require the upstream repository at runtime.

## Build

```bash
mvn clean verify
```

The production artifact is written to `target/GlobiaSlimefun v1.0.0.jar`.

## Operational Notes

- Do not rename registered Slimefun IDs after deployment.
- Back up Slimefun block data before replacing production artifacts.
- A full server restart is required after updating the JAR.
- Existing machines remain valid because this port does not migrate or rewrite stored data.

## License

See [LICENSE](LICENSE). Maintained for DrakesCraft by [DrakesCraft-Labs](https://github.com/DrakesCraft-Labs).

## Qué añade al juego

A generic Slimefun4-Addon that adds some custom stuff for the Globia geopol


Todo se fabrica y se investiga desde la guía normal (`/sf guide`), como cualquier otro contenido
de Slimefun: no hace falta ningún comando especial para empezar.

## Compatibilidad

| | |
|---|---|
| Servidor | Paper / Purpur **1.21.11** |
| Java | **21** |
| Requiere | [Slimefun4-Drake](https://github.com/DrakesCraft-Labs/Slimefun4-Drake) |
| Lado | Solo servidor — quien juega no instala nada |
| Versión | ${project.version} |

## Instalación

1. Descarga el `.jar` de la última versión.
2. Déjalo en la carpeta `plugins/` del servidor, junto a Slimefun.
3. Reinicia el servidor. Los objetos aparecen solos en la guía.

> Este addon está portado al fork de Slimefun de DrakesCraft. Con el Slimefun original puede no
> cargar, porque cambia el espacio de nombres de las clases.

## Créditos
- Fhoz

Port y mantenimiento por **DrakesCraft Labs**. La autoría original es de quien figura arriba; el detalle está en [docs/UPSTREAM_ATTRIBUTION.md](https://raw.githubusercontent.com/DrakesCraft-Labs/GlobiaMachines/main/docs/UPSTREAM_ATTRIBUTION.md).

Licencia **MIT**.

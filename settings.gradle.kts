pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "vakifbank_mobile"
include(":app")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:common")

include(":core:ui")
include(":feature:splash")
include(":core:navigation")
include(":feature:login")
include(":feature:story")
include(":feature:login")

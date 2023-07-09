plugins {
    alias(libs.plugins.indra)
    alias(libs.plugins.indra.publishing)
    alias(libs.plugins.indra.licenser.spotless)
}

group = "org.geysermc.cumulus"

dependencies {
    implementation(libs.gson)
    implementation(libs.guava)

    compileOnlyApi(libs.checker.qual)
}

indra {
    github("GeyserMC", "Cumulus") {
        ci(true)
        issues(true)
        scm(true)
    }

    mitLicense()

    javaVersions {
        target(8)
    }

    publishSnapshotsTo("geysermc", "https://repo.opencollab.dev/maven-snapshots")
    publishReleasesTo("geysermc", "https://repo.opencollab.dev/maven-releases")

    spotless {
        java {
            googleJavaFormat()
            formatAnnotations()
        }
    }
}
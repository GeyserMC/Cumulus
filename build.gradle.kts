import java.net.URI

plugins {
    alias(libs.plugins.indra)
    alias(libs.plugins.indra.publishing)
    alias(libs.plugins.indra.licenser.spotless)
}

group = "org.geysermc.cumulus"

dependencies {
    implementation(libs.gson)

    compileOnlyApi(libs.checker.qual)
    implementation(libs.jspecify)
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

//    publishSnapshotsTo("geysermc", "https://repo.opencollab.dev/maven-snapshots")
//    publishReleasesTo("geysermc", "https://repo.opencollab.dev/maven-releases")

    spotless {
        java {
            googleJavaFormat()
            formatAnnotations()
        }
        ratchetFrom("origin/master")
    }
}

publishing {
    repositories {
        maven {
            name = "geysermc"
            url = URI.create(
                when {
                    project.version.toString().endsWith("-SNAPSHOT") ->
                        "https://repo.opencollab.dev/maven-snapshots"
                    else ->
                        "https://repo.opencollab.dev/maven-releases"
                }
            )
            credentials(PasswordCredentials::class.java)
        }
    }
}

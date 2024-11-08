dependencies {
    api (project(":founderz-application:user-application"))
    api (project(":founderz-application:auth-application"))
    api (project(":founderz-application:tag-application"))
}

val txVersion = "5.3.30";

subprojects {
    dependencies {
        implementation(project(":founderz-common"))
        implementation("org.springframework:spring-tx:$txVersion")
    }
}
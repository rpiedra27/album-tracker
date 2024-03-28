pipeline {
    agent any

    tools{
      maven "jenkins-maven"
      jdk "jdk-21"
    }

    stages {
        stage("Clone") {
            steps{
              checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '50d583eb-1d4e-4c75-b75d-c3ae66a0d99d', url: 'https://github.com/rpiedra27/album-tracker']]])
            }
        }
        stage('Build') {
            steps {
              sh "mvn -B -DskipTests clean package"
            }
        }
        stage('Test') {
            steps {
                sh "mvn test"
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
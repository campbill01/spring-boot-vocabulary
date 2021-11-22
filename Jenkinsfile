pipeline {
  triggers {
    pollSCM 'H/15 * * * *'
}
  options {
    buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
  }

  agent {
    kubernetes {
      yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    jenkins: worker
spec:
  containers:
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    command: ["/busybox/cat"]
    tty: true
    volumeMounts:
      - name: dockercred
        mountPath: "/kaniko/.docker"
  volumes:
    - name: dockercred
      secret:
        secretName: docker
        items:
          - key: .dockerconfigjson
            path: config.json
    """
    }
  }
  stages {
    stage('Stage 1: Build with Kaniko') {
      steps {
        container('kaniko') {
          sh '/kaniko/executor --context=git://github.com/campbill01/spring-boot-vocabulary.git \
                  --destination=docker.io/campbill/spring-boot-vocabulary:latest \
                  --insecure \
                  --skip-tls-verify  \
                  -v=debug'
        }
      }
    }
  }
}
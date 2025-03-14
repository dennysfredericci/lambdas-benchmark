mvn clean install
mv ./target/function.zip ./target/quarkus-lambda.zip
aws s3 cp ./target/quarkus-lambda.zip s3://lambda-deployment-fredericci

SAM_PARAMETERS=$(yq -r 'to_entries | map("ParameterKey=\(.key),ParameterValue=\(.value)") | join(" ")' parameters.yml)
sam deploy --template-file ./template.yaml --stack-name quarkus-lambdas-benchmark --parameter-overrides $SAM_PARAMETERS --capabilities CAPABILITY_IAM
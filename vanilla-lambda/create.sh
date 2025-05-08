mvn clean install
aws s3 cp ./target/vanilla-lambda.jar s3://lambda-deployment-fredericci

SAM_PARAMETERS=$(yq -r 'to_entries | map("ParameterKey=\(.key),ParameterValue=\(.value)") | join(" ")' parameters.yml)
sam deploy --template-file ./template.yaml --stack-name lambdas-benchmark --parameter-overrides $SAM_PARAMETERS --capabilities CAPABILITY_IAM
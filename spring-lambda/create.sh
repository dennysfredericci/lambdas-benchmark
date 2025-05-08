mvn clean install
JAR_FILE=$(find ./target -name "spring-lambda-*-aws-*.jar")

echo "Found JAR file: $JAR_FILE"

TIMESTAMP=$(echo $JAR_FILE | sed -E 's/.*-aws-([0-9]+)\.jar$/\1/')
echo $TIMESTAMP

BASE_FILENAME=$(basename $JAR_FILE)

aws s3 mb s3://lambda-deployment-fredericci
aws s3 cp $JAR_FILE s3://lambda-deployment-fredericci

SAM_PARAMETERS=$(yq -r 'to_entries | map("ParameterKey=\(.key),ParameterValue=\(.value)") | join(" ")' parameters.yml)

# Replace placeholders in the parameters string using sed
SAM_PARAMETERS=$(echo "$SAM_PARAMETERS" | sed "s/ParameterValue=BASE_FILE_NAME/ParameterValue=$BASE_FILENAME/g")
SAM_PARAMETERS=$(echo "$SAM_PARAMETERS" | sed "s/ParameterValue=VERSION/ParameterValue=$TIMESTAMP/g")

sam deploy --template-file ./template.yaml --stack-name lambdas-benchmark --parameter-overrides $SAM_PARAMETERS --capabilities CAPABILITY_IAM

#mv ./target/spring-lambda-0.0.1-SNAPSHOT-aws.jar ./target/spring-lambda.jar
#aws s3 cp ./target/spring-lambda.jar s3://lambda-deployment-fredericci
#SAM_PARAMETERS=$(yq -r 'to_entries | map("ParameterKey=\(.key),ParameterValue=\(.value)") | join(" ")' parameters.yml)
#sam deploy --template-file ./template.yaml --stack-name lambdas-benchmark --parameter-overrides $SAM_PARAMETERS --capabilities CAPABILITY_IAM
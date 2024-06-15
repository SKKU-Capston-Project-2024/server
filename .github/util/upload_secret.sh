#!/bin/bash

# Navigate to the directory containing the resources
cd ./src/main/resources

# List files starting with 'application-' and ending with '.yml'
for file in application-oauth.yml; do
    # Check if the file actually exists to avoid errors with gh command if no files match
    if [[ -f "$file" ]]; then
        # Encode the file content to base64
        content=$(base64 < "$file")


        secret_name="${file%.yml}"
        secret_name="${secret_name//-/_}"

        echo "Setting secret for $file"

        # Set the GitHub secret where the secret name is the filename and the value is the base64 encoded content
        # Replace 'YOUR_REPOSITORY' with your actual GitHub repository in format 'owner/repo'
        gh secret set "$secret_name" --body "$content" --repo "SKKU-Capston-Project-2024/server"
    fi
done

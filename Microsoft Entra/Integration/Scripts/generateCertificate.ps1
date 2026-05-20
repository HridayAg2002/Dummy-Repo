# Create certificate 
$EXOcert = New-SelfSignedCertificate -DnsName "MyOrg.com" -CertStoreLocation "cert:\CurrentUser\My" -NotAfter (Get-Date).AddYears(1) -KeySpec KeyExchange 

# Export certificate to .pfx file 
$EXOcert | Export-PfxCertificate -FilePath EXOcert.pfx -Password (Get-Credential).password 

# Export certificate to .cer file 
$EXOcert | Export-Certificate -FilePath EXOcert.cer
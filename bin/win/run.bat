set ProjectPath=I:\Projects\CredissimoAutomationTestLab\target
cd %ProjectPath%
java -cp tests.jar org.testng.TestNG I:\Projects\CredissimoAutomationTestLab\src\main\resources\xml_files\TestCase_01.xml
pause
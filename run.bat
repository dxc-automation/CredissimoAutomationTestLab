set ProjectPath=G:\Projects\CredissimoAutomationTestLab\target
cd %ProjectPath%
java -cp tests.jar org.testng.TestNG G:\Projects\CredissimoAutomationTestLab\src\main\resources\xml_files\TestCase_01.xml
pause
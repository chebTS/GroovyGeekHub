import groovy.io.FileType

import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: cheb
 * Date: 10/23/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */



//def text = 'Hello world'
//println(text)

class Hello {

    public static void main(String[] args) {

        if (args.length != 1 ){
            println("please enter folder path ")
            return;
        }
        String path = args[0];
        println("Entered dir path: " + path)
        File dir = new File(path);

        def list = []
        dir.eachFileRecurse (FileType.FILES) {
            file -> list << file // O_o
        }
        File tmpFile
        list.each {
            println it.path
            File file = new File(it.path)
            switch (true)     {
                case file.name.startsWith('a_'):
                    file.append("\n"+(file.getText('UTF-8') =~ /a/).count)
                    file.renameTo(file.parent+"/done_"+file.name)
                    break
                case file.name.startsWith("1_"):
                    file.delete()
                    file = new File(it.path)
                    Random random = new Random()
                    int diagsum = 0
                    int randNum;
                    String row
                    for (int i = 0; i < 4; i++) {
                        row  = ""
                        for (int j = 0; j < 4; j++) {
                            randNum = random.nextInt(10)
                            row = row + String.valueOf(randNum) + ' ';
                            if((i==j ) || (i+j == 3)){
                                diagsum = diagsum+ randNum;
                            }
                        }
                        file.append(row+"\n");
                    }
                    file.append(diagsum);
                    file.renameTo(file.parent+"/done_"+file.name)
                    break
                case file.name.startsWith("d_"):
                    file.delete()
                    file = new File(it.path)
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    file.append(sdfDate.format(new Date()))
                    file.renameTo(file.parent+"/done_"+file.name)
                    break
                default:
                    break
            }
        }
    }
}

/*
* "http://groovy.codehaus.org/JN2015-Files

Write script which will process files in folder (folder path should be passed to script as a parameter).
 - each file which name started with ""a_"" should calculate number of ""a"" character in it and write result as a new line to file
 - each file which starts from ""1_"" should replace file content with randomly generated matrix 4 x 4 and sum of main diagonal and antidiagonal from new line
 - each file which starts from ""d_"" should replace file content with date in ""YYYY-MM-DD HH-MM-SS"" format
 - other file should be ignored

All processed files should be renamed with next format: ""done_"" + old name"
* */
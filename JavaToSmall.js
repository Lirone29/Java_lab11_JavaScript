function process(input){
    var temp = input;
    temp = temp.replaceAll("JAVA", "<small> Java</small> ");
    temp = temp.replaceAll("Java", "<small> Java</small> ");
    //return input.toUpperCase();
    return temp;
}
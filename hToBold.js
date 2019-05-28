function process(input){
    var temp = input;
    temp = temp.replaceAll("<h1>", "<h1> <b>");
    temp = temp.replaceAll("</h1>", "</b> </h1>");

    temp = temp.replaceAll("<h2>", "<h2> <b>");
    temp = temp.replaceAll("</h2>", "</b> </h2>");

    temp = temp.replaceAll("<h3>", "<h3> <b>");
    temp = temp.replaceAll("</h3>", "</b> </h3>");

    temp = temp.replaceAll("<h4>", "<h4> <b>");
    temp = temp.replaceAll("</h4>", "</b> </h4>");
    return temp;
}
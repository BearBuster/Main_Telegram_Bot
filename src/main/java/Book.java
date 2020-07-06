import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;

public class Book {
    private Document document;

    public Book(){
        connect();
    }

    private void connect() {
        try {
            document = (Document) Jsoup.connect("https://www.surgebook.com/Kolovrat/book/cheloveku-nuzhen-chelovek1111").get();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getTitle(){
        return document.title();
    }

    public String getLikes(){
        Element element = document.getElementById("likes");
        return element.text();
    }

    public String getDiscripntion(){
        Element element = document.getElementById("description");
        return element.text();
    }

    public String getGeners(){
        Elements elements = document.getElementsByClass("genres d-block");
        return elements.text();
    }

    public String getComentList(){
        Elements elements = document.getElementsByClass("comment_mv1_wrapper_block");

        String comment = elements.text();
        comment = comment.replaceAll("Ответить" , "\n\n");
        comment = comment.replaceAll("Оценить" , "");
        comment = comment.replaceAll("\\d[4]-\\d[2]-\\d[2]" , "");
        comment = comment.replaceAll("\\d[2]-\\d[2]-\\d[2]" , "");
        return comment;
    }
    public String getImg(){
        Elements elements = document.getElementsByClass("cover-book");
        String url = elements.attr("style");
        url = url.replace("background-image: url('" , "");
        url = url.replace("');" , "");
        return url;
    }
    public String getName(){
        Elements elements = document.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis");
        return elements.text();
    }
}

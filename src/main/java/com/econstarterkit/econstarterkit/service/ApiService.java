package com.econstarterkit.econstarterkit.service;

import com.econstarterkit.econstarterkit.entity.Problem;
import com.econstarterkit.econstarterkit.repository.ProblemRepository;
import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.Institution;
import com.econstarterkit.econstarterkit.type.ProblemType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class ApiService {
    @Value("${koreaSecuritiesDepository.apikey}")
    private String apiKey_ksd;

    @Value("${koreaBasicDict.apikey}")
    private String apikey_koreaBasicDict;

    private final ProblemRepository problemRepository;

    public void getApi() throws IOException, ParserConfigurationException, SAXException {
        getApi_koreaSecuritiesDepository();
//        getApi_koreaBank();
    }

    private void getApi_koreaBank() throws IOException, ParserConfigurationException, SAXException {
        for (int i = 0; i < 19055; i++) {       // 19055
            String text = String.valueOf((char)(0xAC00 + i));
            StringBuilder urlBuilder = new StringBuilder("https://krdict.korean.go.kr/api/search"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("key", "UTF-8") + "=" + apikey_koreaBasicDict); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("type_search", "UTF-8") + "=" + URLEncoder.encode("search", "UTF-8")); /*LIKE 검색*/
            urlBuilder.append("&" + URLEncoder.encode("q", "UTF-8") + "=" + URLEncoder.encode(text, "UTF-8")); /*검색어*/
            urlBuilder.append("&" + URLEncoder.encode("advanced", "UTF-8") + "=" + URLEncoder.encode("y", "UTF-8")); /*자세히찾기 여부 */
            urlBuilder.append("&" + URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("start", "UTF-8")); /*검색 방식*/
            urlBuilder.append("&" + URLEncoder.encode("sense_cat", "UTF-8") + "=" + URLEncoder.encode("77", "UTF-8")); /*의미 범주*/
            urlBuilder.append("&" + URLEncoder.encode("pos", "UTF-8") + "=" + URLEncoder.encode("1,2,11", "UTF-8")); /*품사*/

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(urlBuilder.toString());

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList items = doc.getElementsByTagName("item");

            for (int temp = 0; temp < items.getLength(); temp++) {
                Node nNode = items.item(temp);

                Element eElement = (Element) nNode;

                Problem problem = Problem.builder()
                        .description(getTagValue("word", eElement))
                        .correctWord(getTagValue("definition", eElement))
                        .difficulty(Difficulty.NOT_CHECK)
                        .institution(Institution.BASIC_KOREAN_DICTIONARY)
                        .type(ProblemType.WORD)
                        .build();

                problemRepository.save(problem);
            }
        }

        System.out.println("finish");
    }

    public void getApi_koreaSecuritiesDepository() throws IOException, SAXException, ParserConfigurationException {
        StringBuilder urlBuilder = new StringBuilder("https://api.seibro.or.kr/openapi/service/FnTermSvc/getFinancialTermMeaning"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey_ksd); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("term", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*LIKE 검색*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*숫자로 관리*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("2000", "UTF-8")); /*숫자로 관리*/

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(urlBuilder.toString());

        // 제일 첫번째 태그
        doc.getDocumentElement().normalize();

        // 파싱할 tag
        NodeList items = doc.getElementsByTagName("item");

        for (int temp = 0; temp < items.getLength(); temp++) {
            Node nNode = items.item(temp);

            Element eElement = (Element) nNode;

            String[] words = divideWordAndInParentheses(getTagValue("fnceDictNm", eElement));

            Problem problem = Problem.builder()
                    .description(getTagValue("ksdFnceDictDescContent", eElement))
                    .correctWord(words[0])
                    .otherCorrectWord(words[1])
                    .difficulty(Difficulty.NOT_CHECK)
                    .institution(Institution.KOREA_SECURITIES_DEPOSITORY)
                    .type(ProblemType.WORD)
                    .build();

            problemRepository.save(problem);
        }
    }

    private String[] divideWordAndInParentheses(String word) {
        String[] words = word.trim().split("\\(");
        if (words.length == 1) {
            return new String[]{word, ""};
        } else {
            words[1] = words[1].replace(")", "");
            return words;
        }
    }

    public static String getTagValue(String tag, Element eElement) {
        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        result = nlList.item(0).getTextContent();

        result = result.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&nbsp;", "")
                .replaceAll("&amp;", "")
                .replaceAll("&quot;", "")
                .replaceAll("&middot;", "")
                .replaceAll("&divide;", "");
        result = result.replaceAll("<[^>]+>", "").replaceAll("\n", "");

        return result;
    }
}

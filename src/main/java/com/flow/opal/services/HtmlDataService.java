package com.flow.opal.services;

import org.jsoup.nodes.Document;

public interface HtmlDataService {

  Document getHtml();

  String[][] getHtmlContent(Document doc);

  void parseHtmlAndSaveRiverSections(Document doc);

  void parseHtmlAndUpdateWaterLevels(Document doc);


}

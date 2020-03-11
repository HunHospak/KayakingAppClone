package com.flow.opal.services;

import com.flow.opal.exceptionhandling.customexceptions.ConnectionFailedException;
import com.flow.opal.models.entities.RiverSection;
import com.flow.opal.repositories.RiverRepository;
import com.flow.opal.repositories.RiverSectionRepository;
import java.io.IOException;
import java.util.Collection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HtmlDataServiceImpl implements HtmlDataService {

  private RiverRepository riverRepository;
  private RiverSectionRepository riverSectionRepository;
  private String riverDataUrl = "https://www.vizugy.hu/?mapModule=OpVizallas&SzervezetKod=0&mapData=VizmerceLista#mapModule";
  private Collection<String> riverNames;
  private RiverSectionService riverSectionService;

  @Autowired
  public HtmlDataServiceImpl(RiverRepository riverRepository,
      RiverSectionRepository riverSectionRepository,
      RiverSectionService riverSectionService) {
    this.riverRepository = riverRepository;
    this.riverSectionRepository = riverSectionRepository;
    this.riverSectionService = riverSectionService;
  }

  @Override
  public Document getHtml() {
    try {
      return Jsoup.connect(riverDataUrl)
          .validateTLSCertificates(false)
          .get();
    } catch (IOException e) {
      throw new ConnectionFailedException("Can't connect to https://www.vizugy.hu");
    }
  }

  @Override
  public String[][] getHtmlContent(Document doc) {
    Element table = doc.select("table").get(0);
    Elements rows = table.select("tr");
    String[][] content = new String[rows.size() - 1][5];
    for (int i = 1; i < rows.size(); i++) {
      Elements data = rows.get(i).select("td");
      for (int j = 0; j < data.size(); j++) {
        content[i - 1][j] = data.get(j).text();
      }
    }
    return content;
  }

  @Override
  public void parseHtmlAndSaveRiverSections(Document doc) {
    String[][] rowContent = getHtmlContent(doc);
    riverNames = riverRepository.findRiverNames();
    if (riverSectionRepository.findAll().isEmpty()) {
      for (int i = 0; i < rowContent.length; i++) {
        if (riverNames.contains(rowContent[i][1])) {
          RiverSection section = new RiverSection();
          section.setName(rowContent[i][0]);
          riverRepository.findByRiverName(rowContent[i][1]).get().addRiverSection(section);
          section.setKm(Double.parseDouble(rowContent[i][2]));
          section.setWaterLevel(Integer.parseInt(rowContent[i][4]));
          riverSectionService.setCoordinates(section);
          riverSectionService.setIsRunnable(section);
          riverSectionRepository.save(section);
        }
      }
    }
  }

  @Override
  public void parseHtmlAndUpdateWaterLevels(Document doc) {
    String[][] rowContent = getHtmlContent(doc);
    riverNames = riverRepository.findRiverNames();
    if (!riverSectionRepository.findAll().isEmpty()) {
      for (int i = 0; i < rowContent.length; i++) {
        if (riverNames.contains(rowContent[i][1])) {
          RiverSection section = riverSectionRepository.findByName(rowContent[i][0]).get();
          section.setWaterLevel(Double.parseDouble(rowContent[i][4]));
          riverSectionService.setIsRunnable(section);
          riverSectionRepository.save(section);
        }
      }
    }
  }
}

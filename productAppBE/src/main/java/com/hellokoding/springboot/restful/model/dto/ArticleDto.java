package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;
import lombok.Builder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Builder
public class ArticleDto implements Comparable<ArticleDto> {

    private Integer id;
    private List<Movement> movementList;
    private Language language;

    private Date date;
    private String title;
    private String status;
    private String titleRus;

    private String description;
    private String text;
    private String rowColor;
//    private String url;
    private List<UrlLink> linkList;
    private List<ItemConnectionDto> personList;
    private List<ItemConnectionDto> locationList;
    private List<ItemConnectionDto> orgList;
    private List<NameConnectionDto> materialList;
    private List<String> hashtagList;
    private String miscellany;

    private MaterialType mtype;

    //due addition of new constructor (public ArticleDto(Article article, List<ConnectionType> types) error appeares.
    //**********error*******
    //Resolved [org.springframework.http.converter.HttpMessageConversionException: Type definition error:
    // [simple type, class com.hellokoding.springboot.restful.model.dto.ArticleDto];
    // nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
    // Cannot construct instance of `com.hellokoding.springboot.restful.model.dto.ArticleDto`
    // (no Creators, like default construct, exist(sad) cannot deserialize from Object value
    // (no delegate- or property-based Creator)
    //***************
    // So empty default constructor helps. Discussion with Igor.
    //****************
//    лучше вообще Dto не инициализировать через сложные конструкторы. лучше чтобы у дто был просто набор свойств
//    а если тебе его надо создать из какого то другого объекта, сделать конвертер
//    ну ты делаешь статический класс, статический метод, типа ArticleDto from(Article article), и внутри заполняешь поля ArticleDto из сущности
//    например используя этот Builder, builder ломбока позволяет создает тебе автоматически билдер для класса
//    ну то есть не через конструкторы делаешь конвертацию, а через "третий класс"
   //******************
    //Dto should be without logic. Todo?
    public ArticleDto(){

    }

    public ArticleDto(Integer id, List<Movement> movementList, Language language, Date date, String title, String status,
                      String titleRus, String description, String text, String rowColor,
                      List<UrlLink> linkList, List<ItemConnectionDto> personList, List<ItemConnectionDto> locationList,
                      List<ItemConnectionDto> orgList, List<NameConnectionDto> materialList,
                      List<String> hashtagList, String miscellany, MaterialType mtype) {
        this.id = id;
        this.movementList = movementList;
        this.language = language;
        this.mtype = mtype;
        this.date = date;
        this.title = title;
        this.status = status;
        this.titleRus = titleRus;
        this.description = description;
        this.text = text;
        this.rowColor = rowColor;
//        this.url = url;
        this.linkList = linkList;
        this.personList = personList;
        this.locationList = locationList;
        this.orgList = orgList;
        this.materialList = materialList;
        this.hashtagList = hashtagList;
        this.miscellany = miscellany;
    }

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.movementList = article.getMovementList();
        this.language = article.getLanguage();
        this.mtype = article.getMtype();
        this.date = article.getDate();
        this.title = article.getTitle();
        this.status = article.getStatus().getName();
        this.titleRus = article.getTitleRus();
        this.description = article.getDescription();
        this.text = article.getText();
        this.rowColor = article.getRgbSelection();
        this.linkList = article.getLinkList();

        this.personList = new ArrayList<>();
        this.locationList = new ArrayList<>();
        this.orgList = new ArrayList<>();
        this.materialList = new ArrayList<>();

        ItemConnectionDto personConnectionDto;
        for (ArticlePersonConnection connection : article.getPersonConnections()) {
            personConnectionDto = new ItemConnectionDto();
            personConnectionDto.setItemId(connection.getPerson().getId());
            personConnectionDto.setConnection(connection.getConnection());
            personConnectionDto.setComment(connection.getComment());

            this.personList.add(personConnectionDto);
        }

        ItemConnectionDto locationConnectionDto;
        for (ArticleLocationConnection connection : article.getLocationConnections()) {
            locationConnectionDto = new ItemConnectionDto();
            locationConnectionDto.setItemId(connection.getLocation().getId());
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            this.locationList.add(locationConnectionDto);
        }

        ItemConnectionDto orgConnectionDto;
        for (ArticleOrgConnection connection : article.getOrgConnections()) {
            orgConnectionDto = new ItemConnectionDto();
            orgConnectionDto.setItemId(connection.getOrg().getId());
            orgConnectionDto.setConnection(connection.getConnection());
            orgConnectionDto.setComment(connection.getComment());

            this.orgList.add(orgConnectionDto);
        }


        this.hashtagList = new ArrayList<>();
        for ( ArticleHashtag articleHashtag : article.getHashtagList()) {
            if (articleHashtag.getHashtag().equals(articleHashtag.getAssigned_hashtag())) {
                this.hashtagList.add(articleHashtag.getHashtag().getContent());
            }
        }
        this.miscellany = article.getMiscellany();
    }

    public ArticleDto(Article article, List<ConnectionType> types) {
        this.id = article.getId();
        this.movementList = article.getMovementList();
        this.language = article.getLanguage();
        this.mtype = article.getMtype();
        this.date = article.getDate();
        this.title = article.getTitle();
        this.status = article.getStatus().getName();
        this.titleRus = article.getTitleRus();
        this.description = article.getDescription();
        this.text = article.getText();
        this.rowColor = article.getRgbSelection();
        this.linkList = article.getLinkList();

        this.personList = new ArrayList<>();
        this.locationList = new ArrayList<>();
        this.orgList = new ArrayList<>();
        this.materialList = new ArrayList<>();

        ItemConnectionDto personConnectionDto;
        for (ArticlePersonConnection connection : article.getPersonConnections()) {
            personConnectionDto = new ItemConnectionDto();
            personConnectionDto.setItemId(connection.getPerson().getId());
            personConnectionDto.setConnection(connection.getConnection());
            personConnectionDto.setComment(connection.getComment());

            this.personList.add(personConnectionDto);
        }

        ItemConnectionDto locationConnectionDto;
        for (ArticleLocationConnection connection : article.getLocationConnections()) {
            locationConnectionDto = new ItemConnectionDto();
            locationConnectionDto.setItemId(connection.getLocation().getId());
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            this.locationList.add(locationConnectionDto);
        }

        ItemConnectionDto orgConnectionDto;
        for (ArticleOrgConnection connection : article.getOrgConnections()) {
            orgConnectionDto = new ItemConnectionDto();
            orgConnectionDto.setItemId(connection.getOrg().getId());
            orgConnectionDto.setConnection(connection.getConnection());
            orgConnectionDto.setComment(connection.getComment());

            this.orgList.add(orgConnectionDto);
        }

        //replaced to article service (connection types there)
//        ItemConnectionDto materialConnectionDto;
//        for (ArticleMaterialConnection connection : article.getMaterialConnections()) {
//            materialConnectionDto = new ItemConnectionDto();
//            materialConnectionDto.setItemId(connection.getMaterial().getId());
//            materialConnectionDto.setConnection(connection.getConnection().toString());
//            materialConnectionDto.setComment(connection.getComment());
//
//            this.materialList.add(materialConnectionDto);
//        }

//        ArrayList<NameConnectionDto> materialList = new ArrayList<>();
        if (article.getMaterialConnections().size() > 0) {
            NameConnectionDto materialConnectionDto = new NameConnectionDto();
            String dtoName = "";

            for (ArticleMaterialConnection connection : article.getMaterialConnections()) {
                materialConnectionDto = new NameConnectionDto();

                Optional<ConnectionType> matchingObject = types.stream().
                        filter(p -> p.getId().equals(connection.getConnection())).
                        findFirst();

                if (article.getTitleRus() != null) {
                    if (article.getTitleRus().length() > 0) {
                        dtoName += article.getTitleRus();
                        if (article.getTitle() != null) {
                            if (article.getTitle().length() > 0) {
                                dtoName += "/ " + article.getTitle();
                            }
                        }
                    }
                }
                LocalDate dateWithZeroTime = article.getDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if (dateWithZeroTime != null) dtoName += ", " + dateWithZeroTime; //article.getDate().;
                else {
                    dtoName += "," + article.getLinkList().get(0);  //as the first help in fail with date
                }

                materialConnectionDto.setName(dtoName);
                materialConnectionDto.setItemId(connection.getMaterial().getId());
                materialConnectionDto.setConnection(matchingObject.get().getType());
                materialConnectionDto.setComment(connection.getComment());
                dtoName = "";

                this.materialList.add(materialConnectionDto);
            }//for
        }

        this.hashtagList = new ArrayList<>();
        for (ArticleHashtag articleHashtag : article.getHashtagList()) {
            if (articleHashtag.getHashtag().equals(articleHashtag.getAssigned_hashtag())) {
                this.hashtagList.add(articleHashtag.getHashtag().getContent());
            }
        }
        this.miscellany = article.getMiscellany();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Movement> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<Movement> movementList) {
        this.movementList = movementList;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitleRus() {
        return titleRus;
    }

    public void setTitleRus(String titleRus) {
        this.titleRus = titleRus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }

    public List<UrlLink> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<UrlLink> linkList) {
        this.linkList = linkList;
    }

    public List<ItemConnectionDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<ItemConnectionDto> personList) {
        this.personList = personList;
    }

    public List<ItemConnectionDto> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<ItemConnectionDto> locationList) {
        this.locationList = locationList;
    }

    public List<ItemConnectionDto> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<ItemConnectionDto> orgList) {
        this.orgList = orgList;
    }

    public List<NameConnectionDto> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<NameConnectionDto> materialList) {
        this.materialList = materialList;
    }

    public List<String> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

    public String getMiscellany() {
        return miscellany;
    }

    public void setMiscellany(String miscellany) {
        this.miscellany = miscellany;
    }

    public MaterialType getMtype() {
        return mtype;
    }

    public void setMtype(MaterialType mtype) {
        this.mtype = mtype;
    }

    @Override
    public boolean equals(Object obj) {
        return ((ArticleDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(ArticleDto obj) {
        return title.compareToIgnoreCase(obj.getTitle());
    }
}

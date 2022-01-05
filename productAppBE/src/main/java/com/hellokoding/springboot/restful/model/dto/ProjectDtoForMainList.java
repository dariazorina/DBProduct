package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
import java.util.List;

/////////////////////////////////////////NB! for autocomplete in location search//////////////////////////////
public class ProjectDtoForMainList implements Comparable<ProjectDtoForMainList> {

    private Integer id;

    private String status;

    private String name;

    private String rowColor;

    private Integer foundationYear;
    private Integer closureYear;


    private List<String> hashtagList;
    private List<String> tagList;
    private List<String> orgList;
    private List<String> locationList;
    private List<String> personList;
    private List<String> projectList;
    private List<Movement> movementList;

    public ProjectDtoForMainList() {
    }

    public ProjectDtoForMainList(Integer id, List<Movement> movementList, String name,
                                 String rowColor, List<String> hashtagList, List<String> tagList,
                                 List<String> orgList, List<String> locationList, List<String> personList, List<String> projectList,
                                 Integer fYear, Integer cYear, String status) {
        this.id = id;
        this.movementList = movementList;
        this.status = status;
        this.rowColor = rowColor;
        this.name = name;
        this.hashtagList = hashtagList;
        this.tagList = tagList;
        this.orgList = orgList;
        this.projectList = projectList;
        this.locationList = locationList;
        this.personList = personList;
        this.foundationYear = fYear;
        this.closureYear = cYear;
    }

//    public ProjectDtoForMainList(Project project) {
//        this.id = project.getId();
//        this.status = project.getStatus().getName();
//        this.movementList = project.getMovementList();
//        this.rowColor = project.getRgbSelection();
//        this.foundationYear = project.getFoundedYear();
//        this.closureYear = project.getClosedYear();
//
//        this.tagList = new ArrayList<>();
//        for (Tag tag: project.getTagList()){
//            tagList.add(tag.getContent());
//        }
//
//        this.hashtagList = new ArrayList<>();
//        for (ProjectHashtag projectHashtag : project.getHashtagList()) {
//            if (projectHashtag.getHashtag().equals(projectHashtag.getAssigned_hashtag()))
//                this.hashtagList.add(projectHashtag.getHashtag().getContent());
//        }
//
//        this.orgList = new ArrayList<>();
//        this.locationList = new ArrayList<>();
//        this.personList = new ArrayList<>();
//
//
//        List<String> locationStringList = new ArrayList<>();
//        for (ProjectLocationConnection plC : project.getLocationConnections()) {
//            locationStringList.add(plC.getLocation().getCountry());
//        }
//        this.setLocationList(locationStringList);
//
//        List<String> projectStringList = new ArrayList<>();
//        for (ProjectProjectConnection ppC : project.getProjectConnections()) {
//            projectStringList.add(ppC.getProject().getMainTitle());
//        }
//        this.setProjectList(projectStringList);
//
//
//        List<String> personStringList = new ArrayList<>();
//        for (ProjectPersonConnection projPersConn : project.getPersonConnections()) {
//            List<SurnameNamePatr> snpList = projPersConn.getPerson().getSnpList();
//            String snp = "some error";
//            for (SurnameNamePatr nameEl : snpList) {
//                if (nameEl.getPriority() == 1) {
//                    snp = nameEl.getSurname();
//                    if (nameEl.getName().length() > 0) {
//                        snp += " " + nameEl.getName();
//                    }
//                    break;
//                }
//            }
//            if (projPersConn.getConnection().length() > 0) {
//                snp += ", " + projPersConn.getConnection();
//            }
//            personStringList.add(snp);
//        }
//        this.setPersonList(personStringList);
//
//
//        List<String> orgStringList = new ArrayList<>();
//        for (ProjectOrgConnection orgOC : project.getOrgConnections()) {
//            List<OrgName> nameList = orgOC.getOrg().getNameList();
//            String name = "";
//            for (OrgName nameEl : nameList) {
//                if (nameEl.getPriority() == 1) {
//                    name = nameEl.getName();
//                    if (nameEl.getAbbr().length() > 0) {
//                        name += ", " + nameEl.getAbbr();
//                    }
//                    break;
//                }
//            }
//            if (orgOC.getConnection().length() > 0) {
//                name += ", " + orgOC.getConnection();
//            }
//            orgStringList.add(name);
//        }
//        this.setOrgList(orgStringList);
//
//
//        this.name = project.getMainTitle();
//
//        if (project.getOtherTitle() != null && project.getOtherTitle().length() != 0) {
//            this.name += "/ " + project.getOtherTitle();
//        }
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

    public Integer getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(Integer foundationYear) {
        this.foundationYear = foundationYear;
    }

    public Integer getClosureYear() {
        return closureYear;
    }

    public void setClosureYear(Integer closureYear) {
        this.closureYear = closureYear;
    }

    public List<String> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

    public List<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<String> locationList) {
        this.locationList = locationList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }

    public List<String> getPersonList() {
        return personList;
    }

    public void setPersonList(List<String> personList) {
        this.personList = personList;
    }

    public List<Movement> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<Movement> movementList) {
        this.movementList = movementList;
    }


    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public List<String> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<String> projectList) {
        this.projectList = projectList;
    }

    @Override
    public boolean equals(Object obj) {
        return ((ProjectDtoForMainList) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(ProjectDtoForMainList obj) {
        return name.compareToIgnoreCase(obj.name);
    }

}
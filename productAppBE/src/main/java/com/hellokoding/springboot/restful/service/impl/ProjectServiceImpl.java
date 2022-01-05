package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.service.ProjectConverter;
import com.hellokoding.springboot.restful.service.ProjectService;
import com.hellokoding.springboot.restful.service.UrlLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final OrgRepository orgRepository;
    private final OrgNameRepository orgNameRepository;
    private final StatusRepository statusRepository;
    private final HashTagRepository hashTagRepository;
    private final LocationRepository locationRepository;
    private final IsourceRepository isourceRepository;
    private final PersonRepository personRepository;
    private final ArticleRepository articleRepository;
    private final ProjectProjectRepository projectProjectRepository;

    private final UrlLinkService urlLinkService;

    @Override
    public List<Project> findAll() {
        List<Project> all = projectRepository.findAll();
        return all;
    }

    public Integer getQuantityAllProjectsWithMovement(List<Integer> mov) {
        return projectRepository.findAllWithMovement(mov).size();
    }

    public List<ProjectDtoForMainList> findAll(List<Integer> mov, Integer page, Integer size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Project> pageTuts;

        List<ProjectDtoForMainList> dtoAllProjectList = new ArrayList<>();
        pageTuts = projectRepository.findAllWithMovements(paging, mov);

        ProjectDtoForMainList currentProjectDto;
        for (Project pr : pageTuts) {
            currentProjectDto = new ProjectDtoForMainList();
            ProjectConverter.convertToProjectDtoForMainList(pr, currentProjectDto, this);
            dtoAllProjectList.add(currentProjectDto);
        }
        return dtoAllProjectList;
    }

    @Override
    public List<ProjectDtoForMainList> filter(List<String> hash, List<String> name, List<String> location, List<String> org, List<Integer> mov) {
        List<ProjectDtoForMainList> dtoSearchList = new ArrayList<>();
//        Set<Org> searchList = new HashSet<>();
//
//        boolean isSingleFilter = false;
//        int hashCurrentSize = 0;
//        int locationCurrentSize = 0;
//        int nameCurrentSize = 0;
//        int orgCurrentSize = 0;
//
//        List<String> hashList = new ArrayList<>();
//        List<String> nameList = new ArrayList<>();
//        List<String> locationList = new ArrayList<>();
//        List<String> orgList = new ArrayList<>();
//
//        if (hash != null) {
//            hashCurrentSize = hash.size();
//        }
//        if (name != null) {
//            nameCurrentSize = name.size();
//        }
//        if (location != null) {
//            locationCurrentSize = location.size();
//        }
//
//        if (org != null) {
//            orgCurrentSize = org.size();
//        }
//
//        if (hashCurrentSize >= 1) {
//            isSingleFilter = true;
//
//            if (nameCurrentSize >= 1) {
//                isSingleFilter = false;
//
//            } else if (locationCurrentSize >= 1) {
//                isSingleFilter = false;
//
//            } else if (orgCurrentSize >= 1) {
//                isSingleFilter = false;
//            }
//        } else {
//            if (nameCurrentSize >= 1) {
//                isSingleFilter = true;
//
//                if (locationCurrentSize >= 1) {
//                    isSingleFilter = false;
//
//                } else if (orgCurrentSize >= 1) {
//                    isSingleFilter = false;
//                }
//            } else {
//                if (locationCurrentSize >= 1) {
//                    isSingleFilter = true;
//
//                } else if (orgCurrentSize >= 1) {
//                    isSingleFilter = false;
//
//                } else if (orgCurrentSize >= 1) {
//                    isSingleFilter = true;
//                }
//            }
//        }
//
//        if (hash != null && !hash.isEmpty()) {
//            for (String h : hash) hashList.add(h + "%");
//
//            if (isSingleFilter) {
//                for (String s : hashList) searchList.addAll(orgRepository.findByHash(s.toLowerCase(), mov));
//            }
//        }
//
//        if (name != null && !name.isEmpty()) {
//            for (String a : name) nameList.add("%" + a + "%");
////            for (String a : name) nameList.add(a + "%");
//
//            if (isSingleFilter) {
//                for (String s : nameList) searchList.addAll(orgRepository.findByName(s.toLowerCase(), mov));
//            }
//        }
//
//        if (location != null && !location.isEmpty()) {
//            for (String l : location) locationList.add("%" + l + "%");
//
//            if (isSingleFilter) {
//                for (String s : locationList) searchList.addAll(orgRepository.findByLocation(s.toLowerCase(), mov));
//            }
//        }
//
//        if (org != null && !org.isEmpty()) {
//            for (String l : org) orgList.add("%" + l + "%");
//
//            if (isSingleFilter) {
//                for (String s : orgList) searchList.addAll(orgRepository.findByOrg(s.toLowerCase(), mov));
//            }
//        }
//
//        if (!isSingleFilter) {
//            searchList = orgRepository.findByFilters(
//                    hashList.size() == 0 ? null : hashList.get(0).toLowerCase(),
//                    nameList.size() == 0 ? null : nameList.get(0).toLowerCase(),
//                    locationList.size() == 0 ? null : locationList.get(0).toLowerCase(),
//                    orgList.size() == 0 ? null : orgList.get(0).toLowerCase(), mov);
//        }
//
//        OrgDtoForMainList orgDto;
//        for (Org currOrg : searchList) {
//            orgDto = new OrgDtoForMainList(currOrg);
//            dtoSearchList.add(orgDto);
//        }
        return dtoSearchList;
    }

    @Override
    public List<IdContentDto> search(String q) {

        List<Project> proSearchList = new ArrayList<>();
        String query = "%" + q.toLowerCase() + "%";

        proSearchList = projectRepository.findByName(query);
//        System.out.println(orgSearchList);

        if (proSearchList.size() > 0) {
            return transformOriginToDto(proSearchList);
        }

        return null;
    }

    public List<IdContentDto> findByIds(List<Integer> idList) {

//        List<Org> searchRes = new ArrayList<>();
//        for (Integer id : idList) {
//            Optional<Org> l = orgRepository.findById(id);
//
//            if (l != null) {
//                searchRes.add(l.get());
//            }
//        }
//        return transformOriginToDto(searchRes);

        return null;
    }

    public List<OneTypeConnectionDto> findByIdsAndSymmetrically(Integer itemId) {

        Project connectedPro = new Project(), pro = new Project();
        String dtoName = "", connection = "", comment = "";
        List<OneTypeConnectionDto> finalList = new ArrayList<>();
        List<ProjectProjectConnection> searchResSymm = new ArrayList<>();
        Optional<Project> proOpt = projectRepository.findById(itemId);


        if (proOpt.isPresent()) {
            pro = proOpt.get();
            searchResSymm = projectProjectRepository.findByIdSymm(itemId); //searching symm connections for this itemId

            for (ProjectProjectConnection el : pro.getProjectConnections()) { //simple connections
                Optional<Project> connProOpt = projectRepository.findById(el.getConnectedProject().getId());

                if (connProOpt.isPresent()) {
                    connectedPro = connProOpt.get();             //   searchRes.add(l.get());
                    dtoName = connectedPro.getMainTitle();
                    if (connectedPro.getOtherTitle() != null && connectedPro.getOtherTitle().length() != 0) {
                        dtoName += "/ " + connectedPro.getOtherTitle();
                    }
                }
                for (ProjectProjectConnection proProConnection : pro.getProjectConnections()) {
                    if (proProConnection.getConnectedProject().getId().equals(connectedPro.getId())) {
                        connection = proProConnection.getConnection();

                        if (proProConnection.getComment() != null) {
                            if (proProConnection.getComment().length() != 0) {
                                comment = proProConnection.getComment();
                            }
                        }
                    }
                }
                OneTypeConnectionDto proDto = new OneTypeConnectionDto(connectedPro.getId(), dtoName, connection, comment, false);
                finalList.add(proDto);
            }
        }//for

        for (ProjectProjectConnection proProConnection : searchResSymm) {
            if (proProConnection.getConnectedProject().getId().equals(pro.getId())) {

                dtoName = proProConnection.getProject().getMainTitle();
                if (proProConnection.getProject().getOtherTitle() != null && proProConnection.getProject().getOtherTitle().length() > 0) {
                    dtoName += "/ " + proProConnection.getProject().getOtherTitle();
                }
            }

            connection = proProConnection.getConnection();
            if (proProConnection.getComment() != null) {
                if (proProConnection.getComment().length() != 0) {
                    comment = proProConnection.getComment();
                }
            }
            OneTypeConnectionDto proDto = new OneTypeConnectionDto(proProConnection.getProject().getId(), dtoName, connection, comment, true);
            finalList.add(proDto);
        }

        return finalList;
    }

    public List<IdContentDto> transformOriginToDto(List<Project> proList) {
        Set<IdContentDto> fooSet = new TreeSet<>();
        String dtoName;

        for (Project pro : proList) {
            dtoName = pro.getMainTitle();
            if (pro.getOtherTitle() != null && pro.getOtherTitle().length() > 0) {
                dtoName += " /" + pro.getOtherTitle();
            }
            IdContentDto proDto = new IdContentDto(pro.getId(), dtoName);
            fooSet.add(proDto);
        }
        List<IdContentDto> finalList = new ArrayList<>(fooSet);
        return finalList;
    }

    @Override
    public Optional<ProjectDto> findById(Integer id) {

        Optional<Project> proById = projectRepository.findById(id);
        Optional<ProjectDto> proByIdDto;

        proByIdDto = Optional.of(new ProjectDto(proById.get()));

        return proByIdDto;
    }

//    @Override
//    public ProjectDto findById(Integer id) {
//
//        Optional<Project> project = projectRepository.findById(id);
//
//        if (project.isPresent()) {
//            Project pro = project.get();
//
//            ProjectDto projectDto = new ProjectDto();
//            ProjectConverter.convertToProjectDto(pro, projectDto);
//            return projectDto;
//        }
//        return null;
//    }

    @Override
    public Project saveColor(ProjectDtoForMainList projectDto) {

        Project project;
        if (projectRepository.findById(projectDto.getId()).isPresent()) {
            project = projectRepository.findById(projectDto.getId()).get();

            project.setRgbSelection(projectDto.getRowColor());
            return projectRepository.save(project);

        } else
            return null;
    }

    @Override
    public Project save(ProjectDto projectDto) {

        Project project = null;

        List<UrlLink> linkList = projectDto.getLinkList();
        List<UrlLink> linkListWithID = urlLinkService.getLinkListID(linkList);

        if (projectDto.getId() == null) {
            project = new Project();
        } else if (projectRepository.findById(projectDto.getId()).isPresent()) {
            project = projectRepository.findById(projectDto.getId()).get();
        } else
            return null;

        if (project.getLinkList() != null) {
            project.getLinkList().clear();
            projectRepository.flush();
        } else {
            project.setLinkList(new ArrayList<>());
        }

        if (project.getLinkList() == null) {
            project.setLinkList(linkListWithID);
        } else {
            project.getLinkList().addAll(linkListWithID);
        }

        if (project.getTagList() != null) {
            project.getTagList().clear();
            projectRepository.flush();
        } else {
            project.setTagList(new ArrayList<>());
        }

        if (project.getTagList() == null) {
            project.setTagList(projectDto.getTagList());
        } else {
            project.getTagList().addAll(projectDto.getTagList());
        }

        if (project.getMovementList() != null) {
            project.getMovementList().clear();
            projectRepository.flush();
        } else {
            project.setMovementList(new ArrayList<>());
        }
        if (project.getMovementList() == null) {
            project.setMovementList(projectDto.getMovementList());
        } else {
            project.getMovementList().addAll(projectDto.getMovementList());
            //personRepository.flush();
        }


        project.setMainTitle(projectDto.getMainTitle());
        project.setOtherTitle(projectDto.getOtherTitle());

        project.setFoundedYear(projectDto.getFoundationYear());
        project.setClosedYear(projectDto.getClosureYear());
        project.setDescription(projectDto.getDescription());
        project.setMiscellany(projectDto.getMiscellany());
        project.setRgbSelection(projectDto.getRowColor());

        Optional<Status> byName = statusRepository.getByName(projectDto.getStatus());
        if (byName.isPresent()) {
            project.setStatus(byName.get());
        }

        /////////////////project
        if (project.getProjectConnections() != null) {
            project.getProjectConnections().clear();
            projectRepository.flush();
        }

        Integer connectedProjectId;
        boolean isSymmConnection = false;

        Project connectedProject = null;
        ProjectProjectConnection projectProjectConnection;
        ProjectProjectConnection connectedProjectConnection;

        List<ProjectProjectConnection> projectProjectList = new ArrayList<>();
        List<ProjectProjectConnection> connectedProjectList = new ArrayList<>();
        List<ProjectProjectConnection> symmConnectionList;


        for (OneTypeConnectionDto posDto : projectDto.getProjectList()) {
            connectedProjectId = posDto.getItemId();
            if (projectRepository.findById(connectedProjectId).isPresent()) {

                symmConnectionList = projectProjectRepository.findByIdSimple(connectedProjectId);
                for (ProjectProjectConnection projectProjectConn : symmConnectionList) {
                    if (projectProjectConn.getConnectedProject().getId().equals(project.getId())) {
                        isSymmConnection = true;
                        break;
                    }
                }

                connectedProject = projectRepository.findById(connectedProjectId).get();

                if (!isSymmConnection) {

                    projectProjectConnection = new ProjectProjectConnection();
                    projectProjectConnection.setConnection(posDto.getConnection());
                    projectProjectConnection.setComment(posDto.getComment());


                    if (posDto.getIsParent()) {
                        projectProjectConnection.setConnectedProject(project);
                        projectProjectConnection.setProject(connectedProject);

                    } else {
                        projectProjectConnection.setConnectedProject(connectedProject);
                        projectProjectConnection.setProject(project);
                    }
                    projectProjectList.add(projectProjectConnection);

                } else {
                    connectedProjectConnection = new ProjectProjectConnection();
                    if (posDto.getIsParent()) {
                        connectedProjectConnection.setProject(connectedProject);
                        connectedProjectConnection.setConnectedProject(project);
                    } else {
                        connectedProjectConnection.setProject(project);
                        connectedProjectConnection.setConnectedProject(connectedProject);
                    }
                    connectedProjectConnection.setConnection(posDto.getConnection());
                    connectedProjectConnection.setComment(posDto.getComment());

                    connectedProjectList.add(connectedProjectConnection);

                    if (connectedProject.getProjectConnections() == null) {
                        connectedProject.setProjectConnections(connectedProjectList);
                    } else {
                        //remove already existed entities
                        List<ProjectProjectConnection> elList = new ArrayList<>();
                        for (ProjectProjectConnection projectProjectConn : connectedProject.getProjectConnections()) {
                            if (projectProjectConn.getConnectedProject().getId().equals(project.getId())) {
                                elList.add(projectProjectConn);
                            }
                        }
                        connectedProject.getProjectConnections().removeAll(elList);
                        projectRepository.flush();
                        connectedProject.getProjectConnections().addAll(connectedProjectList);
                    }
                }
                isSymmConnection = false;
            }
        }//for (OneTypeConnectionDto posDto : orgDto.getOrgList())

        if (project.getId() != null) {
            //delete symm connections
            List<OneTypeConnectionDto> startListConnectedProjectsForProject = findByIdsAndSymmetrically(project.getId());
            List<OneTypeConnectionDto> resultListConnectedProjectsForProject = projectDto.getProjectList();

            List<OneTypeConnectionDto> differences = startListConnectedProjectsForProject.stream()
                    .filter(element -> !resultListConnectedProjectsForProject.contains(element))
                    .collect(Collectors.toList());

            for (OneTypeConnectionDto nmdto : differences) {
                connectedProject = projectRepository.findById(nmdto.getItemId()).get();

                //remove already existed entities
                List<ProjectProjectConnection> elList = new ArrayList<>();
                for (ProjectProjectConnection projectProjectConn : connectedProject.getProjectConnections()) {
                    if (projectProjectConn.getConnectedProject().getId().equals(project.getId())) {
                        elList.add(projectProjectConn);
                    }
                }
                connectedProject.getProjectConnections().removeAll(elList);
                projectRepository.flush();
            }
        }

        if (project.getProjectConnections() == null) {
            project.setProjectConnections(projectProjectList);
        } else {
            project.getProjectConnections().addAll(projectProjectList);
        }

        /////////////////org////
        if (project.getOrgConnections() != null) {
            project.getOrgConnections().clear();
            projectRepository.flush();
        }

        Integer orgId;
        ProjectOrgConnection orgConnection;
        List<ProjectOrgConnection> orgConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : projectDto.getOrgList()) {

            orgId = connectionDto.getItemId();
            if (orgRepository.findById(orgId).isPresent()) {
                orgConnection = new ProjectOrgConnection();
                orgConnection.setOrg(orgRepository.findById(orgId).get());
                orgConnection.setProject(project);
                orgConnection.setConnection(connectionDto.getConnection());
                orgConnection.setComment(connectionDto.getComment());

                orgConnectionList.add(orgConnection);
            }
        }
        if (project.getOrgConnections() == null) {
            project.setOrgConnections(orgConnectionList);
        } else {
            project.getOrgConnections().addAll(orgConnectionList);
        }

        //////////////location
        if (project.getLocationConnections() != null) {
            project.getLocationConnections().clear();
            projectRepository.flush();
        }

        Integer locatonId;
        ProjectLocationConnection locationConnection;
        List<ProjectLocationConnection> locationConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : projectDto.getLocationList()) {

            locatonId = connectionDto.getItemId();
            if (locationRepository.findById(locatonId).isPresent()) {
                locationConnection = new ProjectLocationConnection();
                locationConnection.setLocation(locationRepository.findById(locatonId).get());
                locationConnection.setProject(project);
                locationConnection.setConnection(connectionDto.getConnection());
                locationConnection.setComment(connectionDto.getComment());

                locationConnectionList.add(locationConnection);
            }
        }

        if (project.getLocationConnections() == null) {
            project.setLocationConnections(locationConnectionList);
        } else {
            project.getLocationConnections().addAll(locationConnectionList);
        }


        ///article
        if (project.getArticleConnections() != null) {
            project.getArticleConnections().clear();
            projectRepository.flush();
        }

        Integer articleId;
        ProjectArticleConnection articleConnection;
        List<ProjectArticleConnection> articleConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : projectDto.getArticleList()) {

            articleId = connectionDto.getItemId();
            if (articleRepository.findById(articleId).isPresent()) {
                articleConnection = new ProjectArticleConnection();
                articleConnection.setArticle(articleRepository.findById(articleId).get());
                articleConnection.setProject(project);
                articleConnection.setConnection(connectionDto.getConnection());
                articleConnection.setComment(connectionDto.getComment());

                articleConnectionList.add(articleConnection);
            }
        }

        if (project.getArticleConnections() == null) {
            project.setArticleConnections(articleConnectionList);
        } else {
            project.getArticleConnections().addAll(articleConnectionList);
        }


//        //event
//        if (project.getIsourceConnections() != null) {
//            project.getIsourceConnections().clear();
//            projectRepository.flush();
//        }
//
//        Integer isourceId;
//        ProjectIsourceConnection isourceConnection;
//        List<ProjectIsourceConnection> isourceConnectionList = new ArrayList<>();
//        for (ItemConnectionDto connectionDto : projectDto.getIsourceList()) {
//
//            isourceId = connectionDto.getItemId();
//            if (isourceRepository.findById(isourceId).isPresent()) {
//                isourceConnection = new ProjectIsourceConnection();
//                isourceConnection.setIsource(isourceRepository.findById(isourceId).get());
//                isourceConnection.setProject(project);
//                isourceConnection.setConnection(connectionDto.getConnection());
//                isourceConnection.setComment(connectionDto.getComment());
//
//                isourceConnectionList.add(isourceConnection);
//            }
//        }
//
//        if (project.getIsourceConnections() == null) {
//            project.setIsourceConnections(isourceConnectionList);
//        } else {
//            project.getIsourceConnections().addAll(isourceConnectionList);
//        }

        ///persons
        if (project.getPersonConnections() != null) {
            project.getPersonConnections().clear();
            projectRepository.flush();
        }

        Integer personId;
        ProjectPersonConnection personConnection;
        List<ProjectPersonConnection> personConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : projectDto.getPersonList()) {

            personId = connectionDto.getItemId();
            if (personRepository.findById(personId).isPresent()) {
                personConnection = new ProjectPersonConnection();
                personConnection.setPerson(personRepository.findById(personId).get());
                personConnection.setProject(project);
                personConnection.setConnection(connectionDto.getConnection());
                personConnection.setComment(connectionDto.getComment());

                personConnectionList.add(personConnection);
            }
        }

        if (project.getPersonConnections() == null) {
            project.setPersonConnections(personConnectionList);
        } else {
            project.getPersonConnections().addAll(personConnectionList);
        }


        /////////////////////project-hashtag////////////////////////
        ProjectHashtag projectHashtag, previousProjectHashtag, previousPreviousProjectHashtag;
        HashTag hashTag, hashTagPrevious, hashTagPreviousPrevious;
        List<ProjectHashtag> hashtagList = new ArrayList<>();
        Integer id;

        if (project.getHashtagList() != null) {
            for (ProjectHashtag projectH : project.getHashtagList()) {
                projectH.setProject(null);
            }
            project.getHashtagList().clear();
            projectRepository.flush();
        } else {
            project.setHashtagList(new ArrayList<>());
        }

        for (String hashtag_content : projectDto.getHashtagList()) {

//            id = hashTagRepository.getHashTagByContent(hashtag_content).getId();
            hashTag = hashTagRepository.getHashTagByContent(hashtag_content);
            projectHashtag = new ProjectHashtag();
//            hashTag = hashTagRepository.findById(id).get();

            if (hashTag.getParentId() == 0) {    ///////////////////////hashtag level 1

                projectHashtag.setHashtag(hashTag);
                projectHashtag.setLevel(1);
                projectHashtag.setAssigned_hashtag(hashTag);
                projectHashtag.setProject(project);

                hashtagList.add(projectHashtag);

            } else {  ///////////////////////hashtag level 2/3

                hashTagPrevious = hashTagRepository.findById(hashTag.getParentId()).get();
                previousProjectHashtag = new ProjectHashtag();

                if (hashTagPrevious.getParentId() == 0) {   ///////////////////////hashtag level 2

                    previousProjectHashtag.setHashtag(hashTagPrevious);
                    previousProjectHashtag.setLevel(1);
                    previousProjectHashtag.setAssigned_hashtag(hashTag);
                    previousProjectHashtag.setProject(project);

                    projectHashtag.setHashtag(hashTag);
                    projectHashtag.setLevel(2);
                    projectHashtag.setAssigned_hashtag(hashTag);
                    projectHashtag.setProject(project);

                    hashtagList.add(projectHashtag);
                    hashtagList.add(previousProjectHashtag);

                } else {   ///////////////////////hashtag level 3

                    hashTagPreviousPrevious = hashTagRepository.findById(hashTagPrevious.getParentId()).get();
                    previousPreviousProjectHashtag = new ProjectHashtag();

                    if (hashTagPreviousPrevious.getParentId() == 0) {

                        previousPreviousProjectHashtag.setHashtag(hashTagPreviousPrevious);
                        previousPreviousProjectHashtag.setLevel(1);
                        previousPreviousProjectHashtag.setAssigned_hashtag(hashTag);
                        previousPreviousProjectHashtag.setProject(project);

                        previousProjectHashtag.setHashtag(hashTagPrevious);
                        previousProjectHashtag.setLevel(2);
                        previousProjectHashtag.setAssigned_hashtag(hashTag);
                        previousProjectHashtag.setProject(project);

                        projectHashtag.setHashtag(hashTag);
                        projectHashtag.setLevel(3);
                        projectHashtag.setAssigned_hashtag(hashTag);
                        projectHashtag.setProject(project);

                        hashtagList.add(projectHashtag);
                        hashtagList.add(previousProjectHashtag);
                        hashtagList.add(previousPreviousProjectHashtag);
                    }
                }//level 3
            } //level 2/3
        }//for

        if (project.getHashtagList() == null) {
            project.setHashtagList(hashtagList);
        } else {
            project.getHashtagList().addAll(hashtagList);
        }

        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Integer id) {
        projectRepository.deleteById(id);
    }
}

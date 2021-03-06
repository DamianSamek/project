package ur.edu.pl.project.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ur.edu.pl.project.model.Project;
import ur.edu.pl.project.model.Role;
import ur.edu.pl.project.model.User;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectRepositoryTest {

@Autowired
private ProjectRepository projectRepository;
private Project project, project2;

    @Before
    public void setUp() throws Exception {
        project = new Project();
        project.setClient("MOTOROLA");
        project.setDescription("project");
        project.setFee(5000);
        project.setFinished(true);
        projectRepository.save(project);

    }

    @After
    public void after() throws Exception {
        //projectRepository.deleteAll();
        projectRepository.delete(project);
       // projectRepository.delete(project2);
    }

//    @Test
//    public void deleteAll_Test() {
//        projectRepository.deleteAll();
//        assertTrue(projectRepository.findAll().isEmpty());
//    }

    @Test
    public void getProjectByClient_Test() {
        boolean add;
        if (projectRepository.findByClient("MOTOROLA")!=null){
            add = true;
        } else add = false;
        assertTrue(add);
    }

    @Test
    public void saveProject_Test() {
        project2 = new Project();
        project2.setClient("NOKIA");
        project2.setDescription("project");
        project2.setFee(6000);
        project2.setFinished(false);
        boolean add;
        if (projectRepository.save(project2) != null) {
            add = true;
            projectRepository.delete(project2);
        } else add = false;
        assertTrue(add);
    }

    @Test
    public void findAllProjects_Test() {
        List<Project> projects = projectRepository.findAll();
        assertNotNull(projects);
        assertTrue(projects.size() > 0);
    }


}
package io.alban.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alban.ppmtool.domain.Backlog;
import io.alban.ppmtool.domain.ProjectTask;
import io.alban.ppmtool.repositories.BacklogRepository;
import io.alban.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		// Exceptions: Project not found

		// PTs to be added to a specific project, project != null
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		// set the bl to pt
		projectTask.setBacklog(backlog);
		// we want our project sequence to be like this: IDPRO-1 IDPRO-2
		Integer BacklogSequence = backlog.getPTSequence();
		// Update the BL SEQUENCE
		BacklogSequence++;

		// Add Sequence to PT
		projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);

		// INITIAL priority when priority null
//		if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
//			projectTask.setPriority(3);
//		}

		// INITIAL status when status is null
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}
		return projectTaskRepository.save(projectTask);
	}
}

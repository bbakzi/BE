package com.sparta.ourportfolio.project.service;

import com.sparta.ourportfolio.common.utils.S3Service;
import com.sparta.ourportfolio.project.dto.ProjectRequestDto;
import com.sparta.ourportfolio.project.dto.ProjectResponseDto;
import com.sparta.ourportfolio.project.dto.ResponseDto;
import com.sparta.ourportfolio.project.entity.Project;
import com.sparta.ourportfolio.project.repository.FileRepository;
import com.sparta.ourportfolio.project.repository.ProjectRepository;
import com.sparta.ourportfolio.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final FileRepository fileRepository;
    private final S3Service s3Service;

    // 프로젝트 작성
    public ResponseDto<?> creatProject(ProjectRequestDto projectRequestDto,
                                       List<MultipartFile> images, User user) throws IOException {
        Project project = new Project(projectRequestDto, user);
        project.setImageFile(s3Service.fileFactory(images, project));
        project = projectRepository.save(project);

        return ResponseDto.setSuccess("프로젝트 작성 완료", null);
    }

    // 프로젝트 전체조회
    public ResponseDto<List<ProjectResponseDto>> getProjects() {
        List<ProjectResponseDto> projectList = projectRepository.findAll().stream().map(ProjectResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("전체 조회 성공", projectList);
    }

    // 프로젝트 상세조회
    public ResponseDto<ProjectResponseDto> getProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("프로젝트가 존재하지 않습니다")
        );
        return ResponseDto.setSuccess("상세 조회 성공", new ProjectResponseDto(project));
    }

    // 프로젝트 수정
    public ResponseDto<?> updateProject(Long id,
                                        ProjectRequestDto projectRequestDto,
                                        List<MultipartFile> images, User user) throws IOException {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("프로젝트가 존재하지 않습니다")
        );

        //USER 확인
        if (!StringUtils.equals(project.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        fileRepository.deleteByProjectId(id); // 해당되는 전체 이미지 삭제
        project.setImageFile(s3Service.fileFactory(images, project));
        project.updateProject(projectRequestDto);
        return ResponseDto.setSuccess("프로젝트 수정 완료.", project);

    }

    // 프로젝트 삭제
    public ResponseDto<?> deleteProject(Long id, User user) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("프로젝트가 존재하지 않습니다")
        );

        //USER 확인
        if (!StringUtils.equals(project.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        projectRepository.deleteById(id);
        return ResponseDto.setSuccess("프로젝트 삭제를 완료했습니다.", null);
    }

}
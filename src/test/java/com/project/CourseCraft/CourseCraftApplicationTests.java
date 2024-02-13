package com.project.CourseCraft;

import com.project.CourseCraft.dao.StaffCourseRepository;
import com.project.CourseCraft.entity.Staff;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CourseCraftApplicationTests {

	@Autowired
	private StaffCourseRepository staffCourseRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	void contextLoads() {
		Staff retrievedStaff = staffCourseRepository.findById(1).orElse(null);
		assertThat(retrievedStaff).isNotNull();

		// Retrieve the staff entity again by its ID
		Staff cachedStaff = staffCourseRepository.findById(1).orElse(null);
		System.out.println(retrievedStaff == cachedStaff);
	}
}

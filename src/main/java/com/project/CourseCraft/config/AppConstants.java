package com.project.CourseCraft.config;

import java.util.Random;

/**
 * Constants used in the application.
 */
public class AppConstants {

    private static final Random random = new Random();

    public static final Integer ADMIN_USER = 1;
    public static final Integer STAFF_USER = 2;
    public static final Integer STUDENT_USER = 3;

    public static Integer autoUserID = random.nextInt(900) + 100;

    /**
     * URLs that require "ADMIN" role for GET requests.
     */
    public static final String[] URL_ADMIN_ONLY_GET = {"/v1/users"};

    /**
     * URLs that require "ADMIN" role for POST requests.
     */
    public static final String[] URL_ADMIN_ONLY_POST = {"/v1/users", "/v1/new-admin"};

    /**
     * URLs that require "ADMIN" role for PUT requests.
     */
    public static final String[] URL_ADMIN_ONLY_PUT = {"/v1/users/{id}", "/v4/enroll-changeExpiry/users/{userID}/course/{courseID}"};

    /**
     * URLs that require "ADMIN" role for DELETE requests.
     */
    public static final String[] URL_ADMIN_ONLY_DELETE = {"/v1/users/{id}"};

    /**
     * URLs that require "ADMIN" or "STAFF" role for GET requests.
     */
    public static final String[] URL_ADMIN_STAFF_GET = {"/v4/enroll/course/{cid}"};

    /**
     * URLs that require "ADMIN" or "STAFF" role for POST requests.
     */
    public static final String[] URL_ADMIN_STAFF_POST = {"/v2/category", "/v3/users/{uID}/category/{categoryID}/course", "/v5/course/{cid}/course-details", "/v5/course/{cid}/course-details/**"};

    /**
     * URLs that require "ADMIN" or "STAFF" role for PUT requests.
     */
    public static final String[] URL_ADMIN_STAFF_PUT = {"/v2/category/{categoryID}", "/v3/course/{id}", "/v5/assignment/{aid}", "/v5/material/{mid}", "/v5/video/{vid}"};

    /**
     * URLs that require "ADMIN" or "STAFF" role for DELETE requests.
     */
    public static final String[] URL_ADMIN_STAFF_DELETE = {"/v2/category/{categoryID}", "/v3/course/{id}", "/v5/assignment/{id}", "/v5/material/{id}", "/v5/video/{id}"};

    /**
     * URLs that require "ADMIN" or "STUDENT" role for GET requests.
     */
    public static final String[] URL_ADMIN_STUDENT_GET = {"/v4/enroll/users/{uid}","/v3/course-not-enrolled/users/{id}"};

    /**
     * URLs that require "ADMIN" or "STUDENT" role for POST requests.
     */
    public static final String[] URL_ADMIN_STUDENT_POST = {"/v4/users/{uid}/course/{cid}/enroll"};

    /**
     * URLs that require "ADMIN" or "STUDENT" role for PUT requests.
     */
    public static final String[] URL_ADMIN_STUDENT_PUT = {""};

    /**
     * URLs that require "ADMIN" or "STUDENT" role for DELETE requests.
     */
    public static final String[] URL_ADMIN_STUDENT_DELETE = {"/v4/enroll/users/{userID}/course/{courseUploadID}"};

}

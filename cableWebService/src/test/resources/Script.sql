INSERT INTO `role` (`role_id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `time_stamp`, `role_name`, `status`) VALUES (1, 1, '2016-07-28 11:31:35', NULL, '2016-07-28 11:31:37', '2016-07-28 11:32:12', 'Admin', 'Active');
INSERT INTO `role` (`role_id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `time_stamp`, `role_name`, `status`) VALUES (2, 1, '2016-07-28 11:31:35', NULL, '2016-07-28 11:31:37', '2016-07-28 11:32:12', 'User', 'Active');
INSERT INTO `role` (`role_id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `time_stamp`, `role_name`, `status`) VALUES (3, 1, '2016-07-28 11:31:35', NULL, '2016-07-28 11:31:37', '2016-07-28 11:32:12', 'Manager', 'Active');

INSERT INTO `organization` (`org_id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `time_stamp`, `address`, `email`, `mobile`, `org_name`, `org_token`, `status`) VALUES (1, 1, '2016-07-28 12:30:11', NULL, NULL, '2016-07-28 12:31:31', '1/5 kuruku street', 'bharathkumar.feb14@gmail.com', '9789944159', 'Cablekart', 'cablekart', 'Active');

INSERT INTO `user` (`user_id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `time_stamp`, `address`, `dob`, `email_id`, `first_name`, `gender`, `last_name`, `login_id`, `mobile`, `password`, `status`, `org_id`) VALUES (1, 1, '2016-07-28 12:40:39', NULL, NULL, '2016-07-28 12:51:24', '1/5 first street,\r\nchennai', '1988-02-14', 'bharathkumar.feb14@gmail.com', 'Admin', 'Male', 'Cablekart', 'admin', '9789944159', 'qeEHbBiCVCTM7aEhrwAZSTnH+wwQlfXOZbURBsQwwXhK7X+8g1dQAs9D', 'Active', 1);

INSERT INTO `user_role` (`user_role_id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `time_stamp`, `status`, `role_id`, `user_id`) VALUES (1, 1, '2016-07-28 12:40:40', NULL, NULL, '2016-07-28 12:52:10', 'Active', 1, 1);


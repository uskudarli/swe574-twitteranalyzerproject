create table users (
	record_id int(10) not null auto_increment,
	email varchar(255) not null,
	password_hash varchar(255) not null,
	full_name varchar(255) not null,
	email_token varchar(255),
	email_confirmed int(1),
	created_at datetime not null,
	primary key (record_id),
	unique key (email)
);

create table tweets (
	record_id int(10) not null auto_increment,
	twitter_tweet_id int(10) not null,
	tweet_time datetime not null,
	tweet_user_id int(10) not null,
	tweet_text varchar(1023) not null,
	primary key (record_id),
	unique key (twitter_tweet_id)
);

create table tweet_ref_users (
	record_id int(10) not null auto_increment,
	tweet_id int(10) not null,
	tweet_ref_user_id int(10) not null,
	primary key (record_id),
	foreign key (tweet_id) references tweets(record_id)
		on delete cascade on update cascade
);

create table tweet_hashtags (
	record_id int(10) not null auto_increment,
	tweet_id int(10) not null,
	hashtag_text varchar(255) not null,
	primary key (record_id),
	foreign key (tweet_id) references tweets(record_id)
		on delete cascade on update cascade
);

create table queries (
	record_id int(10) not null auto_increment,
	user_id int(10) not null,
	query_title varchar(255) not null,
	created_at datetime not null,
	primary key (record_id),
	foreign key (user_id) references users(record_id)
		on delete cascade on update cascade
);

create table query_keywords (
	record_id int(10) not null auto_increment,
	query_id int(10) not null,
	keyword varchar(255) not null,
	primary key (record_id),
	foreign key (query_id) references queries(record_id)
		on delete cascade on update cascade
);

create table query_results (
	record_id int(10) not null auto_increment,
	query_id int(10) not null,
	tweet_id int(10) not null,
	primary key (record_id),
	foreign key (query_id) references queries(record_id)
		on delete cascade on update cascade,
	foreign key (tweet_id) references tweets(record_id)
		on delete cascade on update cascade
);

create table reports (
	record_id int(10) not null auto_increment,
	query_id int(10) not null,
	created_at datetime not null,
	primary key (record_id),
	foreign key (query_id) references queries(record_id)
		on delete cascade on update cascade
);

create table word_frequencies (
	record_id int(10) not null auto_increment,
	report_id int(10) not null,
	word varchar(255) not null,
	word_frequency int(6) not null,
	primary key (record_id),
	foreign key (report_id) references reports(record_id)
		on delete cascade on update cascade
);
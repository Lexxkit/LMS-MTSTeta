DROP TABLE IF EXISTS "categories", "courses", "courses_ratings", "courses_users", "modules", "modules_themes", "themes", "users";


CREATE TABLE "courses"(
    "course_id" BIGINT NOT NULL,
    "author" VARCHAR(255) NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "description" TEXT NOT NULL,
    "duration_weeks" INTEGER NOT NULL,
    "tag" VARCHAR(255) NOT NULL,
    "avg_rating" DOUBLE PRECISION NOT NULL,
    "category_id" BIGINT NOT NULL,
    "created_time" DATE NOT NULL,
    "created_user_id" BIGINT NOT NULL,
    "changed_time" DATE NOT NULL,
    "changed_user_id" BIGINT NOT NULL,
    "deleted_time" DATE NULL,
    "deleted_user_id" BIGINT NULL,
    "is_deleted" BOOLEAN NOT NULL
);
ALTER TABLE
    "courses" ADD PRIMARY KEY("course_id");
CREATE INDEX "courses_author_index" ON
    "courses"("author");
CREATE INDEX "courses_title_index" ON
    "courses"("title");
CREATE INDEX "courses_tag_index" ON
    "courses"("tag");
CREATE INDEX "courses_category_id_index" ON
    "courses"("category_id");
CREATE INDEX "courses_is_deleted_index" ON
    "courses"("is_deleted");
CREATE TABLE "modules"(
    "module_id" BIGINT NOT NULL,
    "course_id" BIGINT NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "description" TEXT NOT NULL,
    "created_time" DATE NOT NULL,
    "created_user_id" BIGINT NOT NULL,
    "changed_time" DATE NOT NULL,
    "changed_user_id" BIGINT NOT NULL,
    "deleted_time" DATE NULL,
    "deleted_user_id" BIGINT NULL,
    "is_deleted" BOOLEAN NOT NULL
);
ALTER TABLE
    "modules" ADD PRIMARY KEY("module_id");
CREATE INDEX "modules_course_id_index" ON
    "modules"("course_id");
CREATE INDEX "modules_is_deleted_index" ON
    "modules"("is_deleted");
CREATE TABLE "themes"(
    "theme_id" BIGINT NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "description" TEXT NOT NULL,
    "content" TEXT NULL,
    "tasks" TEXT NULL,
    "created_time" DATE NOT NULL,
    "created_user_id" BIGINT NOT NULL,
    "changed_time" DATE NOT NULL,
    "changed_user_id" BIGINT NOT NULL,
    "deleted_time" DATE NULL,
    "deleted_user_id" BIGINT NULL,
    "is_deleted" BOOLEAN NOT NULL
);
ALTER TABLE
    "themes" ADD PRIMARY KEY("theme_id");
CREATE INDEX "themes_is_deleted_index" ON
    "themes"("is_deleted");
CREATE TABLE "users"(
    "user_id" BIGINT NOT NULL,
    "nickname" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "first_name" VARCHAR(255) NOT NULL,
    "last_name" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "picture" bytea NULL,
    "is_teacher" BOOLEAN NOT NULL,
    "created_time" DATE NOT NULL,
    "changed_time" DATE NOT NULL,
    "changed_user_id" BIGINT NOT NULL,
    "deleted_time" DATE NULL,
    "deleted_user_id" BIGINT NULL,
    "is_deleted" BOOLEAN NOT NULL
);
ALTER TABLE
    "users" ADD PRIMARY KEY("user_id");
CREATE INDEX "users_first_name_index" ON
    "users"("first_name");
CREATE INDEX "users_last_name_index" ON
    "users"("last_name");
ALTER TABLE
    "users" ADD CONSTRAINT "users_email_unique" UNIQUE("email");
CREATE INDEX "users_is_teacher_index" ON
    "users"("is_teacher");
CREATE INDEX "users_is_deleted_index" ON
    "users"("is_deleted");
CREATE TABLE "courses_users"(
    "course_id" BIGINT NOT NULL,
    "user_id" BIGINT NOT NULL
);
ALTER TABLE
    "courses_users" ADD PRIMARY KEY("course_id", "user_id");
ALTER TABLE
    "courses_users" ADD CONSTRAINT "courses_users_course_id_foreign" FOREIGN KEY("course_id") REFERENCES "courses"("course_id");
ALTER TABLE
    "courses_users" ADD CONSTRAINT "courses_users_user_id_foreign" FOREIGN KEY("user_id") REFERENCES "users"("user_id");
CREATE TABLE "courses_ratings"(
    "course_id" BIGINT NOT NULL,
    "user_id" BIGINT NOT NULL,
    "grade" DOUBLE PRECISION NOT NULL
);
ALTER TABLE
    "courses_ratings" ADD PRIMARY KEY("course_id");
CREATE INDEX "courses_ratings_user_id_index" ON
    "courses_ratings"("user_id");
CREATE TABLE "categories"(
    "category_id" BIGINT NOT NULL,
    "title" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "categories" ADD PRIMARY KEY("category_id");
CREATE TABLE "modules_themes"(
    "module_id" BIGINT NOT NULL,
    "theme_id" BIGINT NOT NULL
);
ALTER TABLE
    "modules_themes" ADD PRIMARY KEY("module_id", "theme_id");
ALTER TABLE
    "modules_themes" ADD CONSTRAINT "modules_themes_module_id_foreign" FOREIGN KEY("module_id") REFERENCES "modules"("module_id");
ALTER TABLE
    "modules_themes" ADD CONSTRAINT "modules_themes_theme_id_foreign" FOREIGN KEY("theme_id") REFERENCES "themes"("theme_id");
ALTER TABLE
    "users" ADD CONSTRAINT "users_changed_user_id_foreign" FOREIGN KEY("changed_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "users" ADD CONSTRAINT "users_deleted_user_id_foreign" FOREIGN KEY("deleted_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "modules" ADD CONSTRAINT "modules_created_user_id_foreign" FOREIGN KEY("created_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "modules" ADD CONSTRAINT "modules_changed_user_id_foreign" FOREIGN KEY("changed_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "modules" ADD CONSTRAINT "modules_deleted_user_id_foreign" FOREIGN KEY("deleted_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "modules" ADD CONSTRAINT "modules_course_id_foreign" FOREIGN KEY("course_id") REFERENCES "courses"("course_id");
ALTER TABLE
    "courses" ADD CONSTRAINT "courses_deleted_user_id_foreign" FOREIGN KEY("deleted_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "courses" ADD CONSTRAINT "courses_category_id_foreign" FOREIGN KEY("category_id") REFERENCES "categories"("category_id");
ALTER TABLE
    "courses" ADD CONSTRAINT "courses_created_user_id_foreign" FOREIGN KEY("created_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "courses" ADD CONSTRAINT "courses_changed_user_id_foreign" FOREIGN KEY("changed_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "themes" ADD CONSTRAINT "themes_created_user_id_foreign" FOREIGN KEY("created_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "themes" ADD CONSTRAINT "themes_changed_user_id_foreign" FOREIGN KEY("changed_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "themes" ADD CONSTRAINT "themes_deleted_user_id_foreign" FOREIGN KEY("deleted_user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "courses_ratings" ADD CONSTRAINT "courses_ratings_user_id_foreign" FOREIGN KEY("user_id") REFERENCES "users"("user_id");
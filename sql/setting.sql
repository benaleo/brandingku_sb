TRUNCATE TABLE settings RESTART IDENTITY;

INSERT INTO settings (secure_id, name, identity, type, value)
VALUES (gen_random_uuid(), 'main logo', 'MAIN_LOGO', 'TEXT', 'https://i.ibb.co/4ZqPbZ3/Logo.png'),
       (gen_random_uuid(), 'floating whatsapp', 'FLOATING_WHATSAPP', 'TEXT', 'https://wa.me/6285117247345'),
       (gen_random_uuid(), 'footer address', 'FOOTER_ADDRESS', 'TEXT', 'Jl. Raya Serang KM 21, Serang, Banten'),
       (gen_random_uuid(), 'footer phone', 'FOOTER_PHONE', 'TEXT', '085117247345'),
       (gen_random_uuid(), 'footer email', 'FOOTER_EMAIL', 'TEXT', 'Y2K6x@example.com'),
       (gen_random_uuid(), 'footer instagram', 'FOOTER_INSTAGRAM', 'TEXT', 'https://www.instagram.com/brandingku.id/'),
       (gen_random_uuid(), 'footer facebook', 'FOOTER_FACEBOOK', 'TEXT', 'https://www.facebook.com/brandingku.id/'),
       (gen_random_uuid(), 'footer twitter', 'FOOTER_TWITTER', 'TEXT', 'https://twitter.com/brandingku_id'),
       (gen_random_uuid(), 'footer youtube', 'FOOTER_YOUTUBE', 'TEXT', 'https://www.youtube.com/channel/UCjvZPQ5Lk4Eh3jD0hJlZ2lQ'),
       (gen_random_uuid(), 'footer linkedin', 'FOOTER_LINKEDIN', 'TEXT', 'https://www.linkedin.com/company/brandingku/');

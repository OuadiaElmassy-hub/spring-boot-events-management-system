
-- ═══════════════════════════════════════════════════════════════
-- EVENTHUB - DONNÉES DE TEST
-- Base : MySQL / PostgreSQL compatible
-- ═══════════════════════════════════════════════════════════════

-- ─────────────────────────────────────────────────────────────
-- 0. NETTOYAGE (ordre inverse des FK)
-- ─────────────────────────────────────────────────────────────
DELETE FROM notification;
DELETE FROM user_settings;
DELETE FROM favorie;
DELETE FROM reservation;
DELETE FROM organisateur;
DELETE FROM evenement;
DELETE FROM utilisateur;
DELETE FROM categorie;

-- Reset auto-increment (MySQL)
ALTER TABLE notification      AUTO_INCREMENT = 1;
ALTER TABLE user_settings      AUTO_INCREMENT = 1;
ALTER TABLE favorie          AUTO_INCREMENT = 1;
ALTER TABLE reservation       AUTO_INCREMENT = 1;
ALTER TABLE organisateur       AUTO_INCREMENT = 1;
ALTER TABLE evenement             AUTO_INCREMENT = 1;
ALTER TABLE utilisateur              AUTO_INCREMENT = 1;
ALTER TABLE categorie          AUTO_INCREMENT = 1;

-- ─────────────────────────────────────────────────────────────
-- 1. VILLES
-- ─────────────────────────────────────────────────────────────
INSERT INTO villes (id, nom) VALUES
(1,  'Casablanca'),
(2,  'Rabat'),
(3,  'Marrakech'),
(4,  'Fès'),
(5,  'Tanger'),
(6,  'Agadir'),
(7,  'Meknès'),
(8,  'Oujda');

-- ─────────────────────────────────────────────────────────────
-- 2. CATÉGORIES
-- ─────────────────────────────────────────────────────────────
INSERT INTO categorie (id, nom) VALUES
(1, 'Concert'),
(2, 'Festival'),
(3, 'Théâtre'),
(4, 'Sport'),
(5, 'Conférence'),
(6, 'Art'),
(7, 'Comédie'),
(8, 'Cinéma');

-- ─────────────────────────────────────────────────────────────
-- 3. USERS
-- Mot de passe : "Password123!" hashé BCrypt (même hash pour tous)
-- Hash BCrypt de "Password123!" :
-- $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- ─────────────────────────────────────────────────────────────
INSERT INTO admin (id, nom, prenom, email, motDePasse, role, telephone, ville, enabled, created_at) VALUES

-- ── ADMIN ───────────────────────────────────────────────────
(1,  'Alaoui',    'Youssef',   'admin@eventhub.ma',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'ADMIN', '0661000001', 'Casablanca', true,  '2024-01-01 08:00:00'),

-- ── ORGANISATEURS ────────────────────────────────────────────
INSERT INTO organisateur (id, nom, prenom, email, motDePasse, role, telephone, ville, enabled, created_at) VALUES

(2,  'Benali',    'Amine',     'amine.benali@evenement.ma',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'ORGANISATEUR', '0661000002', 'Casablanca', true,  '2024-01-15 09:00:00'),

(3,  'Tazi',      'Sarah',     'sarah.tazi@production.ma',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'ORGANISATEUR', '0661000003', 'Rabat', true,  '2024-02-01 10:00:00'),

(4,  'Khadiri',   'Karim',     'karim.khadiri@techconf.ma',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'ORGANISATEUR', '0661000004', 'Marrakech', true,  '2024-02-15 11:00:00'),

(5,  'Atlas',     'Sport',     'sport.atlas@marathon.ma',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'ORGANISATEUR', '0661000005', 'Casablanca', true,  '2024-03-01 09:30:00'),

(6,  'Medina',    'Galerie',   'galerie.medina@art.ma',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'ORGANISATEUR', '0661000006', 'Tanger', true,  '2024-03-10 14:00:00'),

(7,  'ElHassani', 'Omar',      'omar.elhassani@comedy.ma',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'ORGANISATEUR', '0661000007', 'Fès', false, '2024-04-01 10:00:00'),

-- ── CLIENTS ──────────────────────────────────────────────────
INSERT INTO client (id, nom, prenom, email, motDePasse, role, telephone, ville, enabled, created_at) VALUES

(8,  'Martin',    'Sophie',    'sophie.martin@gmail.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'CLIENT', '0661000008', 'Casablanca', true,  '2024-01-20 12:00:00'),

(9,  'Dupont',    'Jean',      'jean.dupont@gmail.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'CLIENT', '0661000009', 'Rabat', true,  '2024-02-05 15:00:00'),

(10, 'Leblanc',   'Marie',     'marie.leblanc@gmail.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'CLIENT', '0661000010', 'Marrakech', true,  '2024-02-20 09:00:00'),

(11, 'Bernard',   'Pierre',    'pierre.bernard@gmail.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'CLIENT', '0661000011', 'Casablanca', true,  '2024-03-05 11:00:00'),

(12, 'Moreau',    'Lucie',     'lucie.moreau@gmail.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'CLIENT', '0661000012', 'Tanger', true,  '2024-03-15 14:30:00'),

(13, 'Simon',     'Thomas',    'thomas.simon@gmail.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'CLIENT', '0661000013', 'Fès', true,  '2024-04-01 08:00:00'),

(14, 'Laurent',   'Emma',      'emma.laurent@gmail.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'CLIENT', '0661000014', 'Agadir', true,  '2024-04-10 10:00:00'),

(15, 'Michel',    'Nicolas',   'nicolas.michel@gmail.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'CLIENT', '0661000015', 'Casablanca', false, '2024-05-01 16:00:00');

-- ─────────────────────────────────────────────────────────────
-- 4. ORGANIZER PROFILES
-- ─────────────────────────────────────────────────────────────
INSERT INTO organisateur
    (id, user_id, organisation_nom, siret, verified, verified_at) VALUES
(1, 2, 'Amine Event Pro',    'MA-123456789', true,  '2024-01-20 10:00:00'),
(2, 3, 'Sarah Production',   'MA-234567890', true,  '2024-02-10 11:00:00'),
(3, 4, 'Karim Dev Conf',     'MA-345678901', true,  '2024-03-01 09:00:00'),
(4, 5, 'Sport Atlas',        'MA-456789012', true,  '2024-03-15 14:00:00'),
(5, 6, 'Galerie Medina',     'MA-567890123', false, NULL),
(6, 7, 'Omar Comedy Club',   'MA-678901234', false, NULL);

-- ─────────────────────────────────────────────────────────────
-- 5. EVENTS
-- statuts : EN_ATTENTE / APPROUVE / SUSPENDU / REJETE
-- ─────────────────────────────────────────────────────────────
INSERT INTO evenement
    (id, titre, description, date, lieu, prix, capacite,
     status, motif, organizer_id, categorie_id, ville_id, created_at)
VALUES

-- ── Événements APPROUVÉS ─────────────────────────────────────
(1,
 'Concert Rock Legends',
 'Une nuit inoubliable avec les plus grands groupes de rock marocain et international. Scène principale et scène acoustique, food trucks, et ambiance unique.',
 '2026-12-15 20:00:00', 'Complexe Mohammed V', 150.00, 5000,
 'APPROUVE', NULL, 2, 1, 1, '2026-05-01 10:00:00'),

(2,
 'Festival Jazz International de Rabat',
 'La 10ème édition du festival de jazz de Rabat réunit 30 artistes de 15 pays. Concerts en plein air, ateliers et master classes.',
 '2026-07-22 19:00:00', 'Théâtre Mohammed V', 200.00, 2000,
 'APPROUVE', NULL, 3, 2, 2, '2026-04-15 09:00:00'),

(3,
 'Conférence Tech & Web3 Maroc',
 'Deux jours de conférences sur la blockchain, l''IA générative et le Web3. 50 speakers internationaux, networking et startup pitches.',
 '2026-08-12 09:00:00', 'Palais des Congrès Marrakech', 350.00, 800,
 'APPROUVE', NULL, 4, 5, 3, '2026-05-10 14:00:00'),

(4,
 'Marathon International de Casablanca',
 'Parcours officiel 42km à travers les plus beaux quartiers de Casablanca. Catégories : marathon complet, semi-marathon et 10km.',
 '2026-09-05 07:00:00', 'Parc de la Ligue Arabe', 80.00, 3000,
 'APPROUVE', NULL, 5, 4, 1, '2026-05-20 11:00:00'),

(5,
 'Exposition Art Moderne Tanger',
 '40 artistes marocains contemporains exposent leurs œuvres. Peinture, sculpture, photographie et art numérique.',
 '2026-10-20 10:00:00', 'Villa des Arts Tanger', 40.00, 500,
 'APPROUVE', NULL, 6, 6, 5, '2026-06-01 16:00:00'),

(6,
 'One Man Show Omar El Hassani',
 'Le comédien Omar El Hassani revient avec son nouveau spectacle "Déconnecté". 2h de rires garantis !',
 '2026-11-10 21:00:00', 'Théâtre Meknès', 120.00, 600,
 'APPROUVE', NULL, 7, 7, 7, '2026-05-25 10:00:00'),

(7,
 'Festival Gnawa Essaouira',
 'Le célèbre festival de musique Gnawa et musiques du monde. 4 jours de concerts gratuits sur la place Moulay Hassan.',
 '2026-06-25 18:00:00', 'Place Moulay Hassan', 0.00, 10000,
 'APPROUVE', NULL, 2, 2, 1, '2026-04-20 09:30:00'),

(8,
 'Championnat Régional de Basketball',
 'Finale du championnat régional de basketball. 8 équipes s''affrontent pour le titre.',
 '2026-07-18 16:00:00', 'Salle Omnisports Agadir', 30.00, 1500,
 'APPROUVE', NULL, 5, 4, 6, '2026-05-05 13:00:00'),

-- ── Événements EN ATTENTE ────────────────────────────────────
(9,
 'Concert Electro Night Casablanca',
 'Une nuit électronique avec les meilleurs DJs marocains et européens. Ambiance unique au bord de l''Atlantique.',
 '2026-10-30 22:00:00', 'Beach Club Aïn Diab', 180.00, 2000,
 'EN_ATTENTE', NULL, 2, 1, 1, '2026-06-01 08:00:00'),

(10,
 'Salon du Livre de Fès',
 'Rencontres avec 80 auteurs, dédicaces, conférences littéraires et ateliers d''écriture créative pour tous les âges.',
 '2026-11-05 09:00:00', 'Palais Batha Fès', 20.00, 1200,
 'EN_ATTENTE', NULL, 6, 6, 4, '2026-06-02 11:00:00'),

(11,
 'Tournoi de Tennis Open Maroc',
 'Tournoi ATP Challenger avec 64 joueurs internationaux. 5 jours de compétition sur terre battue.',
 '2026-09-20 10:00:00', 'Royal Tennis Club Casablanca', 100.00, 2500,
 'EN_ATTENTE', NULL, 5, 4, 1, '2026-06-03 14:00:00'),

-- ── Événements SUSPENDUS ─────────────────────────────────────
(12,
 'Conférence Investissement Crypto',
 'Conférence sur les opportunités d''investissement en cryptomonnaies et actifs numériques.',
 '2026-07-15 14:00:00', 'Hôtel Kenzi Tower Casablanca', 500.00, 300,
 'SUSPENDU',
 'Contenu financier potentiellement trompeur. Vérification des accréditations requise avant réactivation.',
 4, 5, 1, '2026-05-15 09:00:00'),

-- ── Événements REJETÉS ───────────────────────────────────────
(13,
 'Festival Non Autorisé Plage',
 'Festival de musique sur plage privée.',
 '2026-08-01 17:00:00', 'Plage Privée Agadir', 90.00, 800,
 'REJETE',
 'Documents d''autorisation municipale manquants. Dossier incomplet.',
 3, 2, 6, '2026-05-08 15:00:00');

-- ─────────────────────────────────────────────────────────────
-- 6. RÉSERVATIONS
-- statuts : CONFIRME / EN_ATTENTE / ANNULE
-- paiement : PAYE / EN_ATTENTE / REMBOURSE
-- ─────────────────────────────────────────────────────────────
INSERT INTO reservation
    (id, user_id, event_id, statut, paiement, created_at) VALUES

-- ── Sophie (user 8) ───────────────────────────────────────────
(1,  8,  1, 'CONFIRME',   'PAYE',       '2026-05-10 14:00:00'),  -- Concert Rock
(2,  8,  3, 'CONFIRME',   'PAYE',       '2026-05-12 10:30:00'),  -- Conf Tech
(3,  8,  7, 'EN_ATTENTE', 'EN_ATTENTE', '2026-06-01 09:00:00'),  -- Festival Gnawa
(4,  8,  4, 'ANNULE',     'REMBOURSE',  '2026-05-20 16:00:00'),  -- Marathon

-- ── Jean (user 9) ─────────────────────────────────────────────
(5,  9,  1, 'CONFIRME',   'PAYE',       '2026-05-08 11:00:00'),  -- Concert Rock
(6,  9,  2, 'CONFIRME',   'PAYE',       '2026-05-15 13:00:00'),  -- Jazz
(7,  9,  3, 'CONFIRME',   'PAYE',       '2026-05-22 09:00:00'),  -- Conf Tech
(8,  9,  6, 'EN_ATTENTE', 'EN_ATTENTE', '2026-06-02 10:00:00'),  -- Comedy

-- ── Marie (user 10) ───────────────────────────────────────────
(9,  10, 2, 'CONFIRME',   'PAYE',       '2026-05-18 15:00:00'),  -- Jazz
(10, 10, 5, 'CONFIRME',   'PAYE',       '2026-06-01 11:00:00'),  -- Expo Art
(11, 10, 7, 'CONFIRME',   'PAYE',       '2026-05-25 14:00:00'),  -- Gnawa
(12, 10, 8, 'EN_ATTENTE', 'EN_ATTENTE', '2026-06-03 09:30:00'),  -- Basketball

-- ── Pierre (user 11) ──────────────────────────────────────────
(13, 11, 1, 'CONFIRME',   'PAYE',       '2026-05-09 10:00:00'),  -- Concert Rock
(14, 11, 4, 'CONFIRME',   'PAYE',       '2026-05-16 12:00:00'),  -- Marathon
(15, 11, 6, 'ANNULE',     'REMBOURSE',  '2026-05-28 16:00:00'),  -- Comedy

-- ── Lucie (user 12) ───────────────────────────────────────────
(16, 12, 3, 'CONFIRME',   'PAYE',       '2026-05-11 09:00:00'),  -- Conf Tech
(17, 12, 5, 'CONFIRME',   'PAYE',       '2026-06-01 14:00:00'),  -- Expo Art
(18, 12, 2, 'EN_ATTENTE', 'EN_ATTENTE', '2026-06-04 10:00:00'),  -- Jazz

-- ── Thomas (user 13) ──────────────────────────────────────────
(19, 13, 6, 'CONFIRME',   'PAYE',       '2026-05-30 11:00:00'),  -- Comedy
(20, 13, 7, 'CONFIRME',   'PAYE',       '2026-05-20 15:00:00'),  -- Gnawa

-- ── Emma (user 14) ────────────────────────────────────────────
(21, 14, 8, 'CONFIRME',   'PAYE',       '2026-05-25 13:00:00'),  -- Basketball
(22, 14, 4, 'CONFIRME',   'PAYE',       '2026-06-01 09:00:00'),  -- Marathon

-- ── Nicolas (user 15) ─────────────────────────────────────────
(23, 15, 1, 'ANNULE',     'REMBOURSE',  '2026-05-07 10:00:00'),  -- Concert Rock
(24, 15, 2, 'ANNULE',     'REMBOURSE',  '2026-05-14 12:00:00');  -- Jazz

-- ─────────────────────────────────────────────────────────────
-- 7. FAVORIS
-- ─────────────────────────────────────────────────────────────
INSERT INTO favorie (id, user_id, event_id, added_at) VALUES

-- Sophie (user 8)
(1,  8,  2,  '2026-05-15 10:00:00'),  -- Jazz
(2,  8,  5,  '2026-05-20 14:00:00'),  -- Expo Art
(3,  8,  6,  '2026-05-22 11:00:00'),  -- Comedy
(4,  8,  8,  '2026-06-01 09:00:00'),  -- Basketball

-- Jean (user 9)
(5,  9,  4,  '2026-05-10 12:00:00'),  -- Marathon
(6,  9,  5,  '2026-05-18 16:00:00'),  -- Expo Art
(7,  9,  7,  '2026-05-25 10:00:00'),  -- Gnawa

-- Marie (user 10)
(8,  10, 1,  '2026-05-12 09:00:00'),  -- Concert Rock
(9,  10, 3,  '2026-05-22 15:00:00'),  -- Conf Tech
(10, 10, 6,  '2026-05-30 11:00:00'),  -- Comedy

-- Pierre (user 11)
(11, 11, 2,  '2026-05-08 14:00:00'),  -- Jazz
(12, 11, 5,  '2026-05-19 10:00:00'),  -- Expo Art
(13, 11, 7,  '2026-05-28 09:00:00'),  -- Gnawa
(14, 11, 8,  '2026-06-02 12:00:00'),  -- Basketball

-- Lucie (user 12)
(15, 12, 1,  '2026-05-10 11:00:00'),  -- Concert Rock
(16, 12, 4,  '2026-05-20 13:00:00'),  -- Marathon
(17, 12, 6,  '2026-06-01 16:00:00');  -- Comedy

-- ─────────────────────────────────────────────────────────────
-- 8. USER SETTINGS
-- ─────────────────────────────────────────────────────────────
INSERT INTO user_settings
    (id, user_id,
     booking_confirmed, event_reminders, new_recommendations, promotions,
     public_profile, analytics_sharing) VALUES
(1,  8,  true,  true,  true,  false, true,  false),
(2,  9,  true,  true,  false, false, true,  true),
(3,  10, true,  false, true,  true,  false, false),
(4,  11, true,  true,  true,  true,  true,  false),
(5,  12, true,  true,  false, false, true,  false),
(6,  13, false, true,  true,  false, true,  true),
(7,  14, true,  false, false, true,  false, false),
(8,  15, true,  true,  true,  true,  true,  false);

-- ─────────────────────────────────────────────────────────────
-- 9. NOTIFICATIONS
-- user_id NULL = notifications admin globales
-- ─────────────────────────────────────────────────────────────
INSERT INTO notification (id, user_id, message, type, read, created_at) VALUES

-- ── Notifications ADMIN (user_id NULL) ───────────────────────
(1,  NULL, 'Nouvel événement soumis par Amine Event Pro : "Concert Electro Night Casablanca"',
     'EVENT_SUBMITTED', false, '2026-06-01 08:05:00'),

(2,  NULL, 'Nouvel événement soumis par Galerie Medina : "Salon du Livre de Fès"',
     'EVENT_SUBMITTED', false, '2026-06-02 11:10:00'),

(3,  NULL, 'Nouvel événement soumis par Sport Atlas : "Tournoi de Tennis Open Maroc"',
     'EVENT_SUBMITTED', false, '2026-06-03 14:15:00'),

(4,  NULL, 'Nouvel utilisateur inscrit : nicolas.michel@gmail.com',
     'USER_REGISTERED', true,  '2026-05-01 16:05:00'),

(5,  NULL, 'Nouvel organisateur inscrit : Galerie Medina - vérification en attente',
     'ORGANIZER_REGISTERED', true,  '2026-03-10 14:10:00'),

(6,  NULL, 'Nouvel organisateur inscrit : Omar Comedy Club - vérification en attente',
     'ORGANIZER_REGISTERED', false, '2026-04-01 10:05:00'),

(7,  NULL, 'Rapport mensuel Mai 2026 : 142 nouveaux utilisateurs, 630 réservations',
     'MONTHLY_REPORT', true,  '2026-06-01 00:00:00'),

(8,  NULL, 'Compte utilisateur nicolas.michel désactivé par l''administration',
     'USER_SUSPENDED', true,  '2026-05-15 11:00:00'),

-- ── Notifications ORGANISATEURS ──────────────────────────────
(9,  2, 'Votre événement "Concert Rock Legends" a été approuvé',
     'EVENT_STATUS_CHANGED', true,  '2026-05-02 10:00:00'),

(10, 4, 'Votre événement "Conférence Tech & Web3 Maroc" a été approuvé',
     'EVENT_STATUS_CHANGED', true,  '2026-05-11 09:00:00'),

(11, 4, 'Votre événement "Conférence Investissement Crypto" a été suspendu : Contenu financier potentiellement trompeur.',
     'EVENT_STATUS_CHANGED', false, '2026-05-16 14:00:00'),

(12, 3, 'Votre événement "Festival Non Autorisé Plage" a été rejeté : Documents d''autorisation municipale manquants.',
     'EVENT_STATUS_CHANGED', false, '2026-05-09 10:00:00'),

-- ── Notifications CLIENTS ─────────────────────────────────────
(13, 8,  'Votre réservation pour "Concert Rock Legends" est confirmée !',
     'BOOKING_CONFIRMED', true, '2026-05-10 14:05:00'),

(14, 8,  'Rappel : "Concert Rock Legends" dans 7 jours. RDV le 15 Déc 2026 !',
     'EVENT_REMINDER', false, '2026-12-08 09:00:00'),

(15, 9,  'Votre réservation pour "Festival Jazz International" est confirmée !',
     'BOOKING_CONFIRMED', true, '2026-05-15 13:05:00'),

(16, 11, 'Remboursement effectué pour l''annulation de "One Man Show Omar El Hassani"',
     'BOOKING_CANCELLED', true, '2026-05-29 10:00:00'),

(17, 12, 'Votre réservation pour "Conférence Tech & Web3" est confirmée !',
     'BOOKING_CONFIRMED', true, '2026-05-11 09:05:00');


---

-- Vérification des données insérées


-- ─────────────────────────────────────────────────────────────
-- REQUÊTES DE VÉRIFICATION
-- ─────────────────────────────────────────────────────────────

-- Résumé global
SELECT 'users'              AS table_name, COUNT(*) AS total FROM utilisateur
UNION ALL
SELECT 'evenement',            COUNT(*) FROM evenement
UNION ALL
SELECT 'reservations',      COUNT(*) FROM reservations
UNION ALL
SELECT 'favorites',         COUNT(*) FROM favorites
UNION ALL
SELECT 'notifications',     COUNT(*) FROM notifications
UNION ALL
SELECT 'organisateur',COUNT(*) FROM organisateur
UNION ALL
SELECT 'user_settings',     COUNT(*) FROM user_settings;

-- Vérifier les stats admin
SELECT
    COUNT(*)                                                  AS total_users,
    SUM(CASE WHEN role = 'ORGANISATEUR' THEN 1 ELSE 0 END)   AS organisateurs,
    SUM(CASE WHEN role = 'CLIENT'       THEN 1 ELSE 0 END)   AS clients,
    SUM(CASE WHEN enabled = false       THEN 1 ELSE 0 END)   AS desactives
FROM users;

-- Vérifier répartition événements par statut
SELECT status, COUNT(*) AS nb FROM evenement GROUP BY status;

-- Vérifier réservations par statut
SELECT statut, paiement, COUNT(*) AS nb
FROM reservations GROUP BY statut, paiement ORDER BY statut;

-- Vérifier les revenus par organisateur
SELECT
    u.nom           AS organisateur,
    COUNT(e.id)     AS nb_evenement,
    SUM(CASE WHEN r.paiement = 'PAYE' THEN e.prix ELSE 0 END) AS revenus
FROM users u
JOIN evenement e       ON e.organizer_id = u.id
LEFT JOIN reservations r ON r.event_id = e.id
WHERE u.role = 'ORGANISATEUR'
GROUP BY u.id, u.nom
ORDER BY revenus DESC;

-- Vérifier les favoris de Sophie
SELECT u.prenom, e.titre
FROM favorites f
JOIN users u  ON f.user_id  = u.id
JOIN evenement e ON f.event_id = e.id
WHERE u.id = 8;

-- Notifications admin non lues
SELECT COUNT(*) AS non_lues
FROM notifications
WHERE user_id IS NULL AND read = false;

-- Top événements par réservations confirmées
SELECT
    e.titre,
    COUNT(r.id)   AS nb_reservations,
    e.capacite,
    ROUND(COUNT(r.id) * 100.0 / e.capacite, 1) AS taux_remplissage_pct
FROM evenement e
LEFT JOIN reservations r ON r.event_id = e.id AND r.statut = 'CONFIRME'
WHERE e.status = 'APPROUVE'
GROUP BY e.id, e.titre, e.capacite
ORDER BY nb_reservations DESC;

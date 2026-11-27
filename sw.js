// Louanges à Dieu — fonctionnement hors connexion.
// Changer VERSION à chaque mise à jour pour que les téléphones reçoivent la nouvelle version.
const VERSION = 'louanges-v4-1';
const FICHIERS = [
  './',
  './index.html',
  './manifest.webmanifest',
  './icons/icon-192.png',
  './icons/icon-512.png',
  './icons/icon-maskable-512.png',
  './icons/apple-touch-icon.png',
  './icons/favicon.png',
  "./fonts/cormorant-garamond-latin-500-normal.woff2",
  "./fonts/cormorant-garamond-latin-600-italic.woff2",
  "./fonts/cormorant-garamond-latin-600-normal.woff2",
  "./fonts/cormorant-garamond-latin-700-normal.woff2",
  "./fonts/figtree-latin-400-normal.woff2",
  "./fonts/figtree-latin-500-normal.woff2",
  "./fonts/figtree-latin-600-normal.woff2",
  "./fonts/literata-latin-400-italic.woff2",
  "./fonts/literata-latin-400-normal.woff2",
  "./fonts/literata-latin-500-normal.woff2",
];

self.addEventListener('install', e => {
  e.waitUntil(caches.open(VERSION).then(c => c.addAll(FICHIERS)).then(() => self.skipWaiting()));
});

self.addEventListener('activate', e => {
  e.waitUntil(
    caches.keys()
      .then(cles => Promise.all(cles.filter(k => k !== VERSION).map(k => caches.delete(k))))
      .then(() => self.clients.claim())
  );
});

self.addEventListener('fetch', e => {
  if (e.request.method !== 'GET') return;
  const url = new URL(e.request.url);
  if (url.origin !== location.origin) return;
  e.respondWith(
    caches.match(e.request, { ignoreSearch: true }).then(enCache => {
      if (enCache) return enCache;
      return fetch(e.request).then(rep => {
        if (rep.ok) { const copie = rep.clone(); caches.open(VERSION).then(c => c.put(e.request, copie)); }
        return rep;
      }).catch(() => e.request.mode === 'navigate' ? caches.match('./index.html') : Response.error());
    })
  );
});

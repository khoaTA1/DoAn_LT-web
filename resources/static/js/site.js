// site.js - basic helpers
function ajaxPost(url, data, onSuccess, onError){
  fetch(url, {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify(data), credentials: 'same-origin'})
    .then(r=>r.json()).then(onSuccess).catch(onError||function(e){console.error(e)});
}
// sidebar toggle
document.addEventListener('DOMContentLoaded', ()=>{
  const btn = document.querySelector('.sidebar-toggle');
  if(btn){btn.addEventListener('click', ()=>{document.querySelector('.sidebar').classList.toggle('show');});}
});

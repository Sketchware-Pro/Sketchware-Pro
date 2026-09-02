<!DOCTYPE html>
<html lang="bn">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>মাদ্রাসা ভর্তি ফরম</title>

<style>
*{
    box-sizing:border-box;
    font-family:Arial,"Noto Sans Bengali",sans-serif;
}

body{
    margin:0;
    background:#eef5f7;
    color:#18333b;
}

.header{
    background:linear-gradient(135deg,#087f9f,#00658c);
    color:white;
    padding:18px 12px;
    text-align:center;
}

.header h1{
    margin:0;
    font-size:23px;
}

.header p{
    margin:7px 0 0;
    font-size:13px;
}

.container{
    max-width:700px;
    margin:auto;
    padding:15px;
}

.card{
    background:white;
    border-radius:15px;
    padding:18px;
    margin-bottom:15px;
    box-shadow:0 3px 12px #0002;
}

.section-title{
    background:linear-gradient(90deg,#0c83a1,#0b6e94);
    color:white;
    padding:10px 14px;
    border-radius:10px;
    margin:0 0 15px;
    font-size:18px;
}

.grid{
    display:grid;
    grid-template-columns:1fr 1fr;
    gap:12px;
}

@media(max-width:550px){
    .grid{
        grid-template-columns:1fr;
    }
}

label{
    display:block;
    font-weight:bold;
    margin-bottom:5px;
}

input,select,textarea{
    width:100%;
    padding:12px;
    border:1px solid #b9cbd0;
    border-radius:9px;
    font-size:15px;
    outline:none;
}

input:focus,select:focus,textarea:focus{
    border-color:#087f9f;
}

textarea{
    min-height:80px;
    resize:vertical;
}

.btn{
    border:0;
    border-radius:10px;
    padding:13px 16px;
    font-size:16px;
    font-weight:bold;
    cursor:pointer;
    margin:4px;
}

.primary{
    background:#087f9f;
    color:white;
}

.green{
    background:#159447;
    color:white;
}

.orange{
    background:#ed8a0c;
    color:white;
}

.red{
    background:#d93434;
    color:white;
}

.gray{
    background:#68777c;
    color:white;
}

.home-buttons{
    display:grid;
    grid-template-columns:1fr 1fr;
    gap:12px;
}

.home-btn{
    border:0;
    padding:22px 10px;
    border-radius:14px;
    color:white;
    font-size:17px;
    font-weight:bold;
    cursor:pointer;
}

.new{
    background:#159447;
}

.saved{
    background:#087f9f;
}

.searchBtn{
    background:#6949b9;
}

.report{
    background:#e8870c;
}

.hidden{
    display:none;
}

.photo-box{
    width:130px;
    height:150px;
    border:2px dashed #9db5bc;
    border-radius:10px;
    margin:10px auto;
    display:flex;
    justify-content:center;
    align-items:center;
    overflow:hidden;
    background:#f5f7f8;
}

.photo-box img{
    width:100%;
    height:100%;
    object-fit:cover;
}

.form-item{
    border:1px solid #d4e0e3;
    border-radius:12px;
    padding:13px;
    margin-bottom:10px;
    background:#fbfdfe;
}

.form-item strong{
    color:#087f9f;
}

.stats{
    display:grid;
    grid-template-columns:repeat(3,1fr);
    gap:8px;
    margin-top:15px;
}

.stat{
    background:#f0f7f8;
    padding:15px 5px;
    text-align:center;
    border-radius:10px;
}

.stat b{
    display:block;
    font-size:24px;
    color:#087f9f;
}

.small{
    font-size:12px;
    color:#63777c;
}
</style>
</head>

<body>

<!-- HEADER -->
<div class="header">
    <h1>নাদিয়াতুল কোরআন মাদানিয়া মহিলা মাদ্রাসা</h1>
    <p>মজলিশপুর (উত্তরপাড়া), ব্রাহ্মণবাড়িয়া</p>
    <p>ডিজিটাল ভর্তি ফরম</p>
</div>

<div class="container">

<!-- HOME -->
<div id="home">

    <div class="card">
        <h2>🏠 হোম পেজ</h2>
        <p>মাদ্রাসার ডিজিটাল ভর্তি ফরম ব্যবস্থাপনা</p>

        <div class="home-buttons">

            <button class="home-btn new" onclick="showPage('formPage')">
                📝<br>নতুন ফরম
            </button>

            <button class="home-btn saved" onclick="showSaved()">
                📁<br>সংরক্ষিত ফরম
            </button>

            <button class="home-btn searchBtn" onclick="showSearch()">
                🔍<br>ফরম খুঁজুন
            </button>

            <button class="home-btn report" onclick="showReport()">
                📊<br>রিপোর্ট
            </button>

        </div>

        <div class="stats">
            <div class="stat">
                <b id="totalCount">0</b>
                মোট ফরম
            </div>

            <div class="stat">
                <b id="newCount">0</b>
                নতুন
            </div>

            <div class="stat">
                <b id="oldCount">0</b>
                পুরাতন
            </div>
        </div>
    </div>
</div>


<!-- FORM PAGE -->
<div id="formPage" class="hidden">

<div class="card">

    <h2 class="section-title">📝 ভর্তি ফরম</h2>

    <div class="grid">

        <div>
            <label>ফরম নম্বর</label>
            <input id="formNo" placeholder="ফরম নম্বর">
        </div>

        <div>
            <label>তারিখ</label>
            <input id="date" type="date">
        </div>

    </div>

    <br>

    <label>বিভাগ নির্বাচন</label>
    <select id="department">
        <option value="">বিভাগ নির্বাচন করুন</option>
        <option>নূরানী বিভাগ</option>
        <option>কিতাব বিভাগ</option>
        <option>বয়স্ক বিভাগ</option>
        <option>নতুন</option>
        <option>পুরাতন</option>
    </select>

</div>


<div class="card">

<h2 class="section-title">👩 ছাত্রীর তথ্যাবলি</h2>

<div class="grid">

<div>
<label>পূর্ণ নাম</label>
<input id="name" placeholder="ছাত্রীর পূর্ণ নাম">
</div>

<div>
<label>জন্ম তারিখ</label>
<input id="birth" type="date">
</div>

<div>
<label>বয়স</label>
<input id="age" placeholder="বয়স">
</div>

<div>
<label>পিতার নাম</label>
<input id="father" placeholder="পিতার নাম">
</div>

<div>
<label>পেশা</label>
<input id="fatherJob" placeholder="পেশা">
</div>

<div>
<label>স্থায়ী ঠিকানা</label>
<input id="address" placeholder="গ্রাম/মহল্লা">
</div>

<div>
<label>থানা</label>
<input id="thana" placeholder="থানা">
</div>

<div>
<label>জেলা</label>
<input id="district" placeholder="জেলা">
</div>

</div>

<br>

<label>বর্তমান ঠিকানা</label>
<textarea id="currentAddress" placeholder="বর্তমান ঠিকানা"></textarea>

<br>

<label>পূর্ব শিক্ষা প্রতিষ্ঠানের নাম ও ঠিকানা</label>
<textarea id="previousSchool"></textarea>

<br>

<div class="grid">

<div>
<label>যে শ্রেণিতে/বিভাগে পড়েছে</label>
<input id="previousClass">
</div>

<div>
<label>যে শ্রেণিতে ভর্তি হতে ইচ্ছুক</label>
<input id="admissionClass">
</div>

</div>

<br>

<label>ছাত্রীর ছবি</label>

<div class="photo-box">
    <img id="photoPreview" src="" alt="ছবি">
</div>

<input type="file" id="photo" accept="image/*">

</div>


<div class="card">

<h2 class="section-title">👨‍👩‍👧 অভিভাবকের তথ্যাবলি</h2>

<div class="grid">

<div>
<label>পূর্ণ নাম</label>
<input id="guardian" placeholder="অভিভাবকের নাম">
</div>

<div>
<label>পেশা</label>
<input id="guardianJob" placeholder="পেশা">
</div>

<div>
<label>সম্পর্ক</label>
<select id="relation">
<option value="">সম্পর্ক নির্বাচন</option>
<option>পিতা</option>
<option>মাতা</option>
<option>ভাই</option>
<option>অভিভাবক</option>
<option>অন্যান্য</option>
</select>
</div>

<div>
<label>মোবাইল</label>
<input id="mobile" type="tel" placeholder="মোবাইল নম্বর">
</div>

</div>

<br>

<label>অভিভাবকের ঠিকানা</label>
<textarea id="guardianAddress"></textarea>

<br>

<label>অভিভাবকের স্বাক্ষর</label>
<input id="guardianSign" placeholder="স্বাক্ষরের নাম/তথ্য">

</div>


<div class="card">

<h2 class="section-title">📜 অঙ্গীকারনামা</h2>

<p class="small">
আমি মাদ্রাসার নিয়ম-কানুন মেনে চলব এবং প্রতিষ্ঠানের নির্দেশনা অনুসরণ করব।
</p>

<label>
<input type="checkbox" id="studentAgree" style="width:auto">
 ছাত্রীর অঙ্গীকার গ্রহণ করছি
</label>

<br><br>

<label>
<input type="checkbox" id="guardianAgree" style="width:auto">
 অভিভাবকের অঙ্গীকার গ্রহণ করছি
</label>

<br><br>

<button class="btn green" onclick="saveForm()">
💾 ফরম সংরক্ষণ করুন
</button>

<button class="btn gray" onclick="goHome()">
🏠 হোম
</button>

</div>

</div>


<!-- SAVED PAGE -->
<div id="savedPage" class="hidden">

<div class="card">

<h2 class="section-title">📁 সংরক্ষিত ফরমসমূহ</h2>

<div id="savedList"></div>

<button class="btn gray" onclick="goHome()">🏠 হোম</button>

</div>

</div>


<!-- SEARCH PAGE -->
<div id="searchPage" class="hidden">

<div class="card">

<h2 class="section-title">🔍 ফরম খুঁজুন</h2>

<input id="searchInput"
       placeholder="নাম / ফরম নম্বর / মোবাইল"
       oninput="searchForms()">

<br><br>

<div id="searchResults"></div>

<button class="btn gray" onclick="goHome()">🏠 হোম</button>

</div>

</div>


<!-- REPORT -->
<div id="reportPage" class="hidden">

<div class="card">

<h2 class="section-title">📊 রিপোর্ট</h2>

<div class="stats">

<div class="stat">
<b id="rTotal">0</b>
মোট ফরম
</div>

<div class="stat">
<b id="rNoorani">0</b>
নূরানী
</div>

<div class="stat">
<b id="rKitab">0</b>
কিতাব
</div>

</div>

<br>

<button class="btn gray" onclick="goHome()">🏠 হোম</button>

</div>

</div>

</div>


<script>

let forms = JSON.parse(localStorage.getItem("madrasaForms") || "[]");

let editingId = null;


// PAGE SYSTEM
function hideAll(){

    document.getElementById("home").classList.add("hidden");
    document.getElementById("formPage").classList.add("hidden");
    document.getElementById("savedPage").classList.add("hidden");
    document.getElementById("searchPage").classList.add("hidden");
    document.getElementById("reportPage").classList.add("hidden");

}

function showPage(page){

    hideAll();

    document.getElementById(page).classList.remove("hidden");

    if(page==="formPage"){
        if(!editingId){
            clearForm();
            createFormNumber();
        }
    }
}


function goHome(){

    hideAll();

    document.getElementById("home").classList.remove("hidden");

    updateStats();

}


// FORM NUMBER
function createFormNumber(){

    let next = forms.length + 1;

    document.getElementById("formNo").value =
        String(next).padStart(5,"0");

    document.getElementById("date").value =
        new Date().toISOString().split("T")[0];

}


// PHOTO
document.getElementById("photo").addEventListener("change",function(){

    let file = this.files[0];

    if(!file) return;

    let reader = new FileReader();

    reader.onload=function(e){

        document.getElementById("photoPreview").src=e.target.result;

    };

    reader.readAsDataURL(file);

});


// GET VALUE
function val(id){

    return document.getElementById(id).value;

}


// SAVE FORM
function saveForm(){

    if(!val("name").trim()){

        alert("দয়া করে ছাত্রীর নাম লিখুন।");
        return;

    }

    if(!val("formNo").trim()){

        alert("ফরম নম্বর দিন।");
        return;

    }


    let photo =
        document.getElementById("photoPreview").src || "";


    let data = {

        id: editingId || Date.now(),

        formNo:val("formNo"),
        date:val("date"),
        department:val("department"),

        name:val("name"),
        birth:val("birth"),
        age:val("age"),
        father:val("father"),
        fatherJob:val("fatherJob"),
        address:val("address"),
        thana:val("thana"),
        district:val("district"),

        currentAddress:val("currentAddress"),
        previousSchool:val("previousSchool"),
        previousClass:val("previousClass"),
        admissionClass:val("admissionClass"),

        photo:photo,

        guardian:val("guardian"),
        guardianJob:val("guardianJob"),
        relation:val("relation"),
        mobile:val("mobile"),
        guardianAddress:val("guardianAddress"),
        guardianSign:val("guardianSign"),

        studentAgree:
            document.getElementById("studentAgree").checked,

        guardianAgree:
            document.getElementById("guardianAgree").checked

    };


    if(editingId){

        let index =
            forms.findIndex(x=>x.id===editingId);

        forms[index]=data;

        editingId=null;

        alert("ফরমটি সফলভাবে পরিবর্তন করা হয়েছে।");

    }else{

        forms.push(data);

        alert("ফরমটি সফলভাবে সংরক্ষণ করা হয়েছে।");

    }


    localStorage.setItem(
        "madrasaForms",
        JSON.stringify(forms)
    );


    clearForm();

    goHome();

}


// CLEAR
function clearForm(){

    let ids=[

        "formNo","date","department",

        "name","birth","age","father","fatherJob",
        "address","thana","district",
        "currentAddress","previousSchool",
        "previousClass","admissionClass",

        "guardian","guardianJob","relation",
        "mobile","guardianAddress","guardianSign"

    ];


    ids.forEach(id=>{

        document.getElementById(id).value="";

    });


    document.getElementById("photoPreview").src="";

    document.getElementById("studentAgree").checked=false;

    document.getElementById("guardianAgree").checked=false;

}


// SAVED FORMS
function showSaved(){

    hideAll();

    document.getElementById("savedPage")
        .classList.remove("hidden");

    renderSaved();

}


function renderSaved(){

    let box =
        document.getElementById("savedList");

    if(forms.length===0){

        box.innerHTML=
        "<p>কোনো ফরম এখনো সংরক্ষণ করা হয়নি।</p>";

        return;

    }


    box.innerHTML="";


    forms.slice().reverse().forEach(f=>{

        let div=document.createElement("div");

        div.className="form-item";

        div.innerHTML=`

        <strong>ফরম নং: ${f.formNo}</strong><br>

        👩 নাম: ${f.name || "-"}<br>

        📚 বিভাগ: ${f.department || "-"}<br>

        📅 তারিখ: ${f.date || "-"}

        <br><br>

        <button class="btn primary"
        onclick="viewForm(${f.id})">👁️ দেখুন</button>

        <button class="btn orange"
        onclick="editForm(${f.id})">✏️ এডিট</button>

        <button class="btn red"
        onclick="deleteForm(${f.id})">🗑️ ডিলিট</button>

        `;

        box.appendChild(div);

    });

}


// VIEW
function viewForm(id){

    let f=forms.find(x=>x.id===id);

    if(!f)return;


    alert(

`ফরম নম্বর: ${f.formNo}

তারিখ: ${f.date}

বিভাগ: ${f.department}

ছাত্রীর নাম: ${f.name}

জন্ম তারিখ: ${f.birth}

বয়স: ${f.age}

পিতার নাম: ${f.father}

পেশা: ${f.fatherJob}

ঠিকানা: ${f.address}

থানা: ${f.thana}

জেলা: ${f.district}

বর্তমান ঠিকানা: ${f.currentAddress}

ভর্তি শ্রেণি: ${f.admissionClass}

অভিভাবক: ${f.guardian}

সম্পর্ক: ${f.relation}

মোবাইল: ${f.mobile}`

    );

}


// EDIT
function editForm(id){

    let f=forms.find(x=>x.id===id);

    if(!f)return;

    editingId=id;

    showPage("formPage");


    Object.keys(f).forEach(key=>{

        let el=document.getElementById(key);

        if(el && key!=="photo"){

            if(el.type==="checkbox")
                el.checked=f[key];

            else
                el.value=f[key] || "";

        }

    });


    if(f.photo)
        document.getElementById("photoPreview").src=f.photo;

}


// DELETE
function deleteForm(id){

    if(!confirm("আপনি কি এই ফরমটি মুছে ফেলতে চান?"))
        return;


    forms=forms.filter(x=>x.id!==id);

    localStorage.setItem(
        "madrasaForms",
        JSON.stringify(forms)
    );

    renderSaved();

    updateStats();

}


// SEARCH
function showSearch(){

    hideAll();

    document.getElementById("searchPage")
        .classList.remove("hidden");

    searchForms();

}


function searchForms(){

    let q=
        document.getElementById("searchInput").value
        .toLowerCase();

    let result=forms.filter(f=>{

        return (

            (f.name || "").toLowerCase().includes(q) ||

            (f.formNo || "").toLowerCase().includes(q) ||

            (f.mobile || "").includes(q)

        );

    });


    let box=
        document.getElementById("searchResults");

    box.innerHTML="";


    if(result.length===0){

        box.innerHTML="<p>কোনো ফরম পাওয়া যায়নি।</p>";

        return;

    }


    result.forEach(f=>{

        let div=document.createElement("div");

        div.className="form-item";

        div.innerHTML=`

        <strong>${f.name}</strong><br>

        ফরম: ${f.formNo}<br>

        মোবাইল: ${f.mobile || "-"}

        <br><br>

        <button class="btn primary"
        onclick="viewForm(${f.id})">👁️ দেখুন</button>

        `;

        box.appendChild(div);

    });

}


// REPORT
function showReport(){

    hideAll();

    document.getElementById("reportPage")
        .classList.remove("hidden");

    updateReport();

}


function updateReport(){

    document.getElementById("rTotal").innerText=
        forms.length;

    document.getElementById("rNoorani").innerText=
        forms.filter(x=>x.department==="নূরানী বিভাগ").length;

    document.getElementById("rKitab").innerText=
        forms.filter(x=>x.department==="কিতাব বিভাগ").length;

}


// HOME STATS
function updateStats(){

    document.getElementById("totalCount").innerText=
        forms.length;

    document.getElementById("newCount").innerText=
        forms.filter(x=>x.department==="নতুন").length;

    document.getElementById("oldCount").innerText=
        forms.filter(x=>x.department==="পুরাতন").length;

}


updateStats();

</script>

</body>
</html>

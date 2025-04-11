// import { useState, ChangeEvent } from 'react';
// import axios from 'axios';

// const UploadVideo = () => {
//   const [file, setFile] = useState<File | null>(null);
//   const [lang, setLang] = useState("bn");
//   const [teacherId] = useState("123"); // Replace with real teacher ID if needed

//   const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
//     if (e.target.files && e.target.files.length > 0) {
//       setFile(e.target.files[0]);
//     } else {
//       setFile(null);
//     }
//   };

//   const handleLangChange = (e: ChangeEvent<HTMLSelectElement>) => {
//     setLang(e.target.value);
//   };

//   const handleUpload = async () => {
//     if (!file) return alert("Please select a file");

//     const formData = new FormData();
//     formData.append("file", file);
//     formData.append("teacherId", teacherId);
//     formData.append("lang", lang);

//     try {
//       const res = await axios.post("http://localhost:8081/api/video/upload", formData, {
//         headers: {
//           'Content-Type': 'multipart/form-data',
//         },
//       });
//       alert("Uploaded!\nTranscript: " + res.data.transcript);
//     } catch (error) {
//       console.error(error);
//       alert("Upload failed");
//     }
//   };

//   return (
//     <div className="p-6">
//       <h2 className="text-xl font-bold mb-2">Upload a Video</h2>
//       <input type="file" onChange={handleFileChange} />
//       <select value={lang} onChange={handleLangChange} className="ml-2">
//         <option value="bn">Bengali</option>
//         <option value="ml">Malayalam</option>
//         <option value="kn">Kannada</option>
//         <option value="gu">Gujarati</option>
//         <option value="te">Telugu</option>
//         <option value="ta">Tamil</option>
//       </select>
//       <button
//         onClick={handleUpload}
//         className="ml-4 bg-blue-500 text-white px-3 py-1 rounded"
//       >
//         Upload
//       </button>
//     </div>
//   );
// };

// export default UploadVideo;

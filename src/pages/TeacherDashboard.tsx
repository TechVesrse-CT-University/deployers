import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { Upload, Video, BookOpen, Users, Clock, Award } from 'lucide-react';

const TeacherDashboard = () => {
  const [isDragging, setIsDragging] = useState(false);
  const [file, setFile] = useState<File | null>(null);

  const handleDrop = (e: React.DragEvent) => {
    e.preventDefault();
    setIsDragging(false);
    const droppedFile = e.dataTransfer.files[0];
    if (droppedFile?.type.startsWith('video/') || droppedFile?.type.startsWith('audio/')) {
      setFile(droppedFile);
    }
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFile = e.target.files?.[0];
    if (selectedFile?.type.startsWith('video/') || selectedFile?.type.startsWith('audio/')) {
      setFile(selectedFile);
    }
  };

  // Mock data for statistics
  const stats = [
    { icon: BookOpen, label: 'Total Lectures', value: '12' },
    { icon: Users, label: 'Students Reached', value: '156' },
    { icon: Clock, label: 'Hours of Content', value: '24' },
    { icon: Award, label: 'Languages', value: '4' }
  ];

  // Mock data for recent uploads
  const recentUploads = [
    { title: 'Introduction to Calculus', views: 45, translations: 3 },
    { title: 'Chemical Bonding', views: 32, translations: 2 }
  ];

  return (
    <div className="min-h-screen py-20 px-4">
      <div className="max-w-6xl mx-auto">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="flex items-center justify-between mb-12"
        >
          <h1 className="text-4xl font-bold text-white">Teacher Dashboard</h1>
          <div className="flex gap-4">
            <button className="px-4 py-2 bg-white/10 text-white rounded-lg hover:bg-white/20 transition">
              View Analytics
            </button>
            <button className="px-4 py-2 bg-gradient-to-r from-blue-500 to-purple-500 text-white rounded-lg hover:opacity-90 transition">
              New Upload
            </button>
          </div>
        </motion.div>
        
        {/* Statistics */}
        <div className="grid grid-cols-4 gap-6 mb-12">
          {stats.map((stat, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: index * 0.1 }}
              whileHover={{ scale: 1.05 }}
              className="bg-white/10 p-6 rounded-xl backdrop-blur-sm"
            >
              <div className="flex items-center gap-4">
                <stat.icon className="w-8 h-8 text-blue-400" />
                <div>
                  <h3 className="text-2xl font-bold text-white">{stat.value}</h3>
                  <p className="text-gray-300">{stat.label}</p>
                </div>
              </div>
            </motion.div>
          ))}
        </div>

        <div className="grid md:grid-cols-3 gap-8">
          {/* Upload Section */}
          <motion.div
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            className="md:col-span-2 bg-white/10 p-8 rounded-xl backdrop-blur-sm"
          >
            <h2 className="text-2xl font-bold text-white mb-6">Upload New Content</h2>

            <div
              className={`border-2 border-dashed rounded-lg p-12 text-center ${
                isDragging ? 'border-blue-500 bg-blue-500/10' : 'border-gray-600'
              }`}
              onDragOver={(e) => {
                e.preventDefault();
                setIsDragging(true);
              }}
              onDragLeave={() => setIsDragging(false)}
              onDrop={handleDrop}
            >
              {file ? (
                <motion.div
                  initial={{ opacity: 0, scale: 0.9 }}
                  animate={{ opacity: 1, scale: 1 }}
                  className="space-y-4"
                >
                  <Video className="w-16 h-16 mx-auto text-blue-400" />
                  <p className="text-white">{file.name}</p>
                  <button
                    className="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition"
                    onClick={() => {
                      // Handle upload here
                      console.log('Uploading:', file);
                    }}
                  >
                    Start Upload
                  </button>
                </motion.div>
              ) : (
                <div className="space-y-4">
                  <Upload className="w-16 h-16 mx-auto text-gray-400" />
                  <p className="text-gray-300">
                    Drag and drop your video or audio file here, or click to select
                  </p>
                  <input
                    type="file"
                    accept="video/*,audio/*"
                    className="hidden"
                    onChange={handleFileChange}
                    id="file-upload"
                  />
                  <label
                    htmlFor="file-upload"
                    className="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition cursor-pointer inline-block"
                  >
                    Select File
                  </label>
                </div>
              )}
            </div>
          </motion.div>

          {/* Recent Uploads */}
          <motion.div
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            className="bg-white/10 p-8 rounded-xl backdrop-blur-sm"
          >
            <h2 className="text-2xl font-bold text-white mb-6">Recent Uploads</h2>
            <div className="space-y-4">
              {recentUploads.map((upload, index) => (
                <motion.div
                  key={index}
                  initial={{ opacity: 0, y: 10 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ delay: index * 0.1 }}
                  className="bg-white/5 p-4 rounded-lg"
                >
                  <h3 className="text-white font-semibold mb-2">{upload.title}</h3>
                  <div className="flex justify-between text-sm text-gray-300">
                    <span>{upload.views} views</span>
                    <span>{upload.translations} translations</span>
                  </div>
                </motion.div>
              ))}
            </div>
          </motion.div>
        </div>
      </div>
    </div>
  );
};

export default TeacherDashboard;
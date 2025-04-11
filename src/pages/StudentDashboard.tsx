import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { MessageCircle, Video, Download, BookOpen, Clock } from 'lucide-react';

const StudentDashboard = () => {
  const [selectedLanguage, setSelectedLanguage] = useState('');
  const [showChatbot, setShowChatbot] = useState(false);

  // Mock data for videos
  const videos = [
    {
      id: 1,
      title: 'Introduction to Calculus',
      teacher: 'Dr. Sarah Johnson',
      subject: 'Mathematics',
      duration: '45:30',
      thumbnail: 'https://images.unsplash.com/photo-1635070041078-e363dbe005cb',
    },
    {
      id: 2,
      title: 'Chemical Bonding Basics',
      teacher: 'Prof. Michael Chen',
      subject: 'Chemistry',
      duration: '38:15',
      thumbnail: 'https://images.unsplash.com/photo-1532634993-5f2f421a8cac',
    },
  ];

  // Mock data for progress
  const progress = [
    { icon: BookOpen, label: 'Courses Enrolled', value: '4' },
    { icon: Clock, label: 'Hours Watched', value: '12.5' },
  ];

  return (
    <div className="min-h-screen py-20 px-4">
      <div className="max-w-6xl mx-auto">
        <h1 className="text-4xl font-bold text-white mb-8">Student Dashboard</h1>

        {/* Progress Statistics */}
        <div className="grid grid-cols-2 gap-6 mb-8">
          {progress.map((stat, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: index * 0.1 }}
              className="bg-white/10 p-6 rounded-xl backdrop-blur-sm"
            >
              <div className="flex items-center gap-4">
                <stat.icon className="w-8 h-8 text-blue-400" />
                <div>
                  <h3 className="text-xl font-bold text-white">{stat.value}</h3>
                  <p className="text-gray-300">{stat.label}</p>
                </div>
              </div>
            </motion.div>
          ))}
        </div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="grid md:grid-cols-3 gap-8"
        >
          <div className="md:col-span-2 space-y-6">
            <h2 className="text-3xl font-bold text-white mb-6">Available Lectures</h2>

            {videos.map((video) => (
              <motion.div
                key={video.id}
                whileHover={{ scale: 1.02 }}
                className="bg-white/10 p-6 rounded-xl backdrop-blur-sm"
              >
                <div className="flex gap-4">
                  <div className="w-40 h-24 rounded-lg overflow-hidden">
                    <img
                      src={video.thumbnail}
                      alt={video.title}
                      className="w-full h-full object-cover"
                    />
                  </div>
                  <div className="flex-1">
                    <h3 className="text-xl font-bold text-white mb-2">
                      {video.title}
                    </h3>
                    <p className="text-gray-300 mb-2">
                      {video.teacher} • {video.subject}
                    </p>
                    <div className="flex items-center gap-4">
                      <select
                        className="bg-white/5 border border-gray-600 rounded px-3 py-1 text-white"
                        value={selectedLanguage}
                        onChange={(e) => setSelectedLanguage(e.target.value)}
                      >
                        <option value="">Select Language</option>
                        <option value="bengali">Bengali</option>
                        <option value="hindi">Hindi</option>
                        <option value="tamil">Tamil</option>
                        <option value="punjabi">Punjabi</option>
                      </select>
                      <button
                        className="flex items-center gap-2 px-4 py-1 bg-blue-500 text-white rounded hover:bg-blue-600 transition"
                        disabled={!selectedLanguage}
                      >
                        <Download size={16} />
                        Translate & Download
                      </button>
                    </div>
                  </div>
                </div>
              </motion.div>
            ))}
          </div>

          <div className="relative">
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              className="bg-white/10 p-6 rounded-xl backdrop-blur-sm sticky top-20"
            >
              <button
                className="w-full flex items-center justify-center gap-2 px-4 py-3 bg-gradient-to-r from-blue-500 to-purple-500 text-white rounded-lg hover:opacity-90 transition"
                onClick={() => setShowChatbot(!showChatbot)}
              >
                <MessageCircle size={20} />
                Open AI Chatbot
              </button>

              {showChatbot && (
                <div className="mt-4 bg-white/5 rounded-lg p-4">
                  <div className="h-96 overflow-y-auto space-y-4">
                    <p className="text-gray-300">
                      Hi! How can I help you with your studies today?
                    </p>
                  </div>
                  <div className="mt-4">
                    <input
                      type="text"
                      placeholder="Type your question..."
                      className="w-full p-2 rounded bg-white/5 border border-gray-600 text-white"
                    />
                  </div>
                </div>
              )}
            </motion.div>
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default StudentDashboard;
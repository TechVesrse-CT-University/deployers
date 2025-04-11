import React from 'react';
import { motion } from 'framer-motion';
import { BookOpen, Languages, Video, MessageCircle, School, Users, Globe, Award } from 'lucide-react';

const languages = [
  { name: 'বাংলা', lang: 'Bengali' },
  { name: 'हिंदी', lang: 'Hindi' },
  { name: 'தமிழ்', lang: 'Tamil' },
  { name: 'ਪੰਜਾਬੀ', lang: 'Punjabi' }
];

const features = [
  {
    icon: Video,
    title: 'Video Translation',
    description: 'Upload educational content in any language and translate it to multiple Indian languages automatically.'
  },
  {
    icon: Languages,
    title: 'Multiple Languages',
    description: 'Support for Bengali, Hindi, Tamil, Punjabi and more languages coming soon.'
  },
  {
    icon: MessageCircle,
    title: 'AI Chatbot Support',
    description: 'Get instant answers to your doubts with our intelligent chatbot system.'
  }
];

const stats = [
  { number: '50+', label: 'Partner Institutions' },
  { number: '10K+', label: 'Students Benefited' },
  { number: '1000+', label: 'Hours of Content' },
  { number: '4', label: 'Languages Supported' }
];

const Home = () => {
  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <div className="py-20 px-4 relative overflow-hidden">
        <div className="max-w-7xl mx-auto text-center relative z-10">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
          >
            <h1 className="text-7xl font-bold text-white mb-6 leading-tight">
              Breaking Language Barriers<br />in Education
            </h1>
            <p className="text-xl text-gray-300 max-w-3xl mx-auto mb-12">
              Transform educational content into multiple Indian languages. 
              Empowering students and teachers across the nation with accessible learning.
            </p>
          </motion.div>

          {/* Language Carousel */}
          <div className="flex justify-center gap-8 mt-12 mb-20">
            {languages.map((lang, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, x: -20 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ delay: index * 0.2 }}
                whileHover={{ scale: 1.05 }}
                className="bg-white/5 backdrop-blur-sm p-6 rounded-xl"
              >
                <h3 className="text-3xl font-bold text-white mb-2">{lang.name}</h3>
                <p className="text-gray-300">{lang.lang}</p>
              </motion.div>
            ))}
          </div>

          {/* Stats Section */}
          <div className="grid grid-cols-4 gap-8 mb-20">
            {stats.map((stat, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: index * 0.1 }}
                className="bg-white/5 backdrop-blur-sm p-6 rounded-xl"
              >
                <h3 className="text-4xl font-bold text-white mb-2">{stat.number}</h3>
                <p className="text-gray-300">{stat.label}</p>
              </motion.div>
            ))}
          </div>
        </div>

        {/* Floating Educational Elements */}
        <div className="absolute top-0 left-0 w-full h-full overflow-hidden pointer-events-none">
          {['∫', 'π', '∑', 'θ', '√', 'sin', 'cos', 'tan'].map((symbol, index) => (
            <motion.div
              key={index}
              className="absolute text-white/10 text-6xl font-bold"
              initial={{
                x: Math.random() * window.innerWidth,
                y: Math.random() * window.innerHeight,
              }}
              animate={{
                y: [0, -20, 0],
                rotate: [0, 360],
              }}
              transition={{
                duration: Math.random() * 5 + 5,
                repeat: Infinity,
                ease: "linear",
              }}
            >
              {symbol}
            </motion.div>
          ))}
        </div>
      </div>

      {/* Features Section */}
      <div className="py-20 px-4 bg-gradient-to-b from-transparent to-black/30">
        <div className="max-w-7xl mx-auto">
          <motion.h2 
            initial={{ opacity: 0 }}
            whileInView={{ opacity: 1 }}
            className="text-4xl font-bold text-white text-center mb-16"
          >
            Why Choose EduVoice?
          </motion.h2>

          <div className="grid md:grid-cols-3 gap-8">
            {features.map((feature, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                transition={{ delay: index * 0.2 }}
                whileHover={{ scale: 1.05 }}
                className="bg-white/10 p-8 rounded-xl backdrop-blur-sm"
              >
                <feature.icon className="text-blue-400 w-12 h-12 mb-4" />
                <h3 className="text-xl font-bold text-white mb-2">{feature.title}</h3>
                <p className="text-gray-300">{feature.description}</p>
              </motion.div>
            ))}
          </div>
        </div>
      </div>

      {/* Partnership Section */}
      <div className="py-20 px-4">
        <div className="max-w-7xl mx-auto">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            className="text-center mb-16"
          >
            <h2 className="text-4xl font-bold text-white mb-6">Partner With Us</h2>
            <p className="text-xl text-gray-300 max-w-3xl mx-auto">
              Join the growing network of educational institutions transforming the way students learn across language barriers.
            </p>
          </motion.div>

          <div className="grid md:grid-cols-2 gap-12">
            <motion.div
              initial={{ opacity: 0, x: -20 }}
              whileInView={{ opacity: 1, x: 0 }}
              className="bg-white/10 p-8 rounded-xl backdrop-blur-sm"
            >
              <School className="text-purple-400 w-12 h-12 mb-4" />
              <h3 className="text-2xl font-bold text-white mb-4">For Schools</h3>
              <ul className="space-y-3 text-gray-300">
                <li>✓ Access to multilingual educational content</li>
                <li>✓ Customized learning paths for students</li>
                <li>✓ Real-time progress tracking</li>
                <li>✓ Dedicated support team</li>
              </ul>
            </motion.div>

            <motion.div
              initial={{ opacity: 0, x: 20 }}
              whileInView={{ opacity: 1, x: 0 }}
              className="bg-white/10 p-8 rounded-xl backdrop-blur-sm"
            >
              <Globe className="text-pink-400 w-12 h-12 mb-4" />
              <h3 className="text-2xl font-bold text-white mb-4">For Colleges</h3>
              <ul className="space-y-3 text-gray-300">
                <li>✓ Advanced content translation tools</li>
                <li>✓ Integration with existing LMS</li>
                <li>✓ Analytics and reporting</li>
                <li>✓ Custom feature development</li>
              </ul>
            </motion.div>
          </div>
        </div>
      </div>

      {/* Call to Action */}
      <div className="py-20 px-4">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          className="max-w-4xl mx-auto text-center"
        >
          <h2 className="text-4xl font-bold text-white mb-6">Ready to Transform Education?</h2>
          <p className="text-xl text-gray-300 mb-8">
            Join us in making quality education accessible to everyone, regardless of their language.
          </p>
          <button className="px-8 py-4 bg-gradient-to-r from-blue-500 to-purple-500 text-white rounded-lg text-xl font-bold hover:opacity-90 transition">
            Get Started Today
          </button>
        </motion.div>
      </div>
    </div>
  );
};

export default Home;
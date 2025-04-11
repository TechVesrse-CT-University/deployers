import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate();
  const [isTeacher, setIsTeacher] = useState(true);
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // In a real app, you would validate credentials here
    if (isTeacher) {
      navigate('/teacher-dashboard');
    } else {
      navigate('/student-dashboard');
    }
  };

  return (
    <div className="min-h-screen py-20 px-4">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="max-w-md mx-auto bg-white/10 p-8 rounded-xl backdrop-blur-sm"
      >
        <h2 className="text-3xl font-bold text-white mb-6 text-center">Login</h2>

        <div className="flex justify-center space-x-4 mb-6">
          <button
            className={`px-4 py-2 rounded ${
              isTeacher
                ? 'bg-blue-500 text-white'
                : 'bg-white/5 text-gray-300'
            }`}
            onClick={() => setIsTeacher(true)}
          >
            Teacher
          </button>
          <button
            className={`px-4 py-2 rounded ${
              !isTeacher
                ? 'bg-blue-500 text-white'
                : 'bg-white/5 text-gray-300'
            }`}
            onClick={() => setIsTeacher(false)}
          >
            Student
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-gray-300 mb-1">Email</label>
            <input
              type="email"
              className="w-full p-2 rounded bg-white/5 border border-gray-600 text-white"
              value={formData.email}
              onChange={(e) =>
                setFormData({ ...formData, email: e.target.value })
              }
              required
            />
          </div>

          <div>
            <label className="block text-gray-300 mb-1">Password</label>
            <input
              type="password"
              className="w-full p-2 rounded bg-white/5 border border-gray-600 text-white"
              value={formData.password}
              onChange={(e) =>
                setFormData({ ...formData, password: e.target.value })
              }
              required
            />
          </div>

          <button
            type="submit"
            className="w-full py-2 px-4 bg-gradient-to-r from-blue-500 to-purple-500 text-white rounded hover:opacity-90 transition"
          >
            Login
          </button>
        </form>
      </motion.div>
    </div>
  );
};

export default Login;
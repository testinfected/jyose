define 'yose', :group => 'com.vtence.yose', :version => '0.1-SNAPSHOT' do
  compile.options.source = '1.7'
  compile.options.target = '1.7'

  compile.with :molecule, :simple
  test.with :hamcrest, :jmock

  package(:jar).tap do |jar|
      jar.merge artifacts(:molecule, :simple)
      jar.with :manifest => manifest.merge( 'Main-Class' => 'com.vtence.yose.Yose' )
  end

  task :run => :package do
    Java::Commands.java("-jar", project.package.to_s, ENV['PORT']) { exit }
  end
end
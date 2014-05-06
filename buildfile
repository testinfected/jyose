define 'jyose', :group => 'com.vtence.jyose', :version => '0.3-SNAPSHOT' do
  compile.options.source = '1.7'
  compile.options.target = '1.7'

  compile.with :molecule, :simple, :gson, :mustache
  test.with :hamcrest, :jmock, transitive(artifacts(:htmlunit)), :juniversalchardet, :molecule_tests

  package(:jar).tap do |jar|
      jar.merge artifacts(:molecule, :simple, :gson, :mustache)
      jar.with :manifest => manifest.merge( 'Main-Class' => 'com.vtence.jyose.JYose' )
  end

  task :run => :package do
    Java::Commands.java("-jar", project.package.to_s, ENV['PORT'], _(:src, :main, :webapp)) {
      exit
    }
  end
end
package com.vtence.jyose.fire;

public enum Move {
    Right {
        @Override
        public Pos from(Pos initial) {
            return initial.right();
        }
    },
    Down {
        @Override
        public Pos from(Pos initial) {
            return initial.down();
        }
    },
    Left {
        @Override
        public Pos from(Pos initial) {
            return initial.left();
        }
    };

    public abstract Pos from(Pos initial);
}
